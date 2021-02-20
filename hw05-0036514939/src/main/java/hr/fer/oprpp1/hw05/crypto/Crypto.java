package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Cubi
 *
 */
public class Crypto {
	private static final String CHECKSHA = "checksha";
	private static final String ENCRYPT = "encrypt";
	private static final String DECRYPT = "decrypt";
	
	private static final int BUFFER_SIZE = 4096;

	public static void main(String[] args) throws IOException {
		if (args.length <= 0) {
			throw new IllegalArgumentException("First argument provided is not valid. Valid ones are: " + CHECKSHA
					+ ", " + ENCRYPT + " and " + DECRYPT);
		}

		switch (args[0]) {
		case CHECKSHA:
			if (args.length != 2) {
				throw new IllegalArgumentException(
						"Expecting 2 arguments for digesting file: keyword (checksha) and file name, but got "
								+ args.length + " arguments!");
			}
			digestFile(args[1]);
			break;
		case ENCRYPT:
			if (args.length != 3) {
				throw new IllegalArgumentException(
						"Expecting 3 arguments for encrypting: keyword (encrypt) and 2 file names, but got "
								+ args.length + " arguments!");
			}
			encryptFile(args[1], args[2]);
			break;
		case DECRYPT:
			if (args.length != 3) {
				throw new IllegalArgumentException(
						"Expecting 3 arguments for decrypting: keyword (decrypt) and 2 file names, but got "
								+ args.length + " arguments!");
			}
			decryptFile(args[1], args[2]);
			break;
		default:
			throw new IllegalArgumentException("First argument provided is not valid. Valid ones are: " + CHECKSHA
					+ ", " + ENCRYPT + " and " + DECRYPT);
		}
	}

	private static void digestFile(String fileName) throws IOException {
		System.out.println("Please provide expected sha-256 digest for " + fileName + ":");
		System.out.format("> ");

		String checksum = "";

		try (InputStream is = new BufferedInputStream(new FileInputStream(fileName));
				Scanner sc = new Scanner(System.in)) {
			checksum = sc.nextLine();
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;

			while ((read = is.read(buffer)) == BUFFER_SIZE) {
				sha.update(buffer);
			}

			byte[] finalBuffer = new byte[read];
			for (int i = 0; i < read; i++) {
				finalBuffer[i] = buffer[i];
			}

			byte[] dig = sha.digest(finalBuffer);

			if (Util.bytetohex(dig).equals(checksum)) {
				System.out.println("Digesting completed. Digest of " + fileName + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + fileName
						+ " does not match the expected digest. Digest was: " + Util.bytetohex(dig));
			}

		} catch (FileNotFoundException e) {
			System.err.println("File " + fileName + " not found");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private static void encryptFile(String fileName, String cryptedFileName) {
		cipherFile(fileName, cryptedFileName, Cipher.ENCRYPT_MODE);
	}

	private static void decryptFile(String cryptedFileName, String origFileName) {
		cipherFile(cryptedFileName, origFileName, Cipher.DECRYPT_MODE);
	}
	
	private static void cipherFile(String sourceFileName, String destFileName, int mode) {		
		String keyText;
		String ivText;

		try (Scanner sc = new Scanner(System.in);
				InputStream is = new BufferedInputStream(new FileInputStream(sourceFileName));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName))) {
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.format("> ");
			keyText = sc.nextLine();

			System.out.println(
					"Please provide initialization vector as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.format("> ");
			ivText = sc.nextLine();

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, keySpec, paramSpec);

			byte[] buffer = new byte[BUFFER_SIZE];
			int read;

			while ((read = is.read(buffer)) == BUFFER_SIZE) {
				os.write(cipher.update(buffer));
			}

			byte[] finalBuffer = new byte[read];
			for (int i = 0; i < read; i++) {
				finalBuffer[i] = buffer[i];
			}
			
			os.write(cipher.doFinal(finalBuffer));

			if(mode == Cipher.ENCRYPT_MODE) {
				System.out.println("Encryption completed. Generated file " + destFileName + " based on file " + sourceFileName + ".");
			} else {
				System.out.println("Decryption completed. Generated file " + destFileName + " based on file " + sourceFileName + ".");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
}
