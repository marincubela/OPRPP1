package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.utils.Util;

public class HexdumpShellCommand implements ShellCommand {
	private final String name = "hexdump";
	private static final int BUFFER_SIZE = 16;
	
	private final List<String> description;

	public HexdumpShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to produce hex-output for a given file.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> split = Util.parseArguments(arguments);

		if (split.size() != 1) {
			throw new IllegalArgumentException(
					"hexdump command has one argument, but was given " + split.size() + " arguments!");
		}

		Path source = Paths.get(split.get(0));
		if (!Files.exists(source)) {
			throw new IllegalArgumentException("Source file does not exists. Please provide file that exists!");
		}

		if (Files.isDirectory(source)) {
			throw new IllegalArgumentException("hexdump command only works with files and not with directories!");
		}

		try (InputStream is = new BufferedInputStream(new FileInputStream(source.toString()))) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;
			int j = 0;

			while ((read = is.read(buffer)) == BUFFER_SIZE) {
				env.writeln(print(buffer, j++));
			}

			byte[] finalBuffer = new byte[read];
			for (int i = 0; i < read; i++) {
				finalBuffer[i] = buffer[i];
			}
			
			env.writeln(printFinal(finalBuffer, j));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Source file does not exists. Please provide file that exists!");
		} catch (IOException e) {
			throw new ShellIOException(e);
		}

		return ShellStatus.CONTINUE;
	}

	private String printFinal(byte[] buffer, int j) {
		String res = new String("");
		res += String.format("%08d:", j * 10);
		int i = 0;
		for(i = 0; i < 16; i++) {
			if(i != 8) {
				res += " ";
			} else {
				res += "|";
			}
			if(i >= buffer.length) {
				res += "  ";
			} else {
				res += bytetohex(buffer[i]);				
			}
		}
		
		res += " | ";

		for(byte b : buffer) {
			if(b > 32 && b < 127) {
				res += (char) b;
			} else {
				res += '.';
			}
		}
		
		return res;
	}

	private String print(byte[] buffer, int j) {
		String res = new String("");
		res += String.format("%08d:", j * 10);
		
		for(int i = 0; i < 16; i++) {
			if(i != 8) {
				res += " ";
			} else {
				res += "|";
			}
			res += bytetohex(buffer[i]);
		}
		
		res += " | ";

		for(byte b : buffer) {
			if(b > 32 && b < 127) {
				res += (char) b;
			} else {
				res += '.';
			}
		}
		
		return res;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * Metoda koja pretvara polje bajtova u heksadekadski zapis pomoću stringa.
	 * 
	 * @param bytearray polje bajtova koje se pretvara.
	 * @return heksadekadsi prikaz polja bajtova.
	 */
	public static String bytetohex(byte b) {
		String res = new String("");
		int n1, n2, a;
		a = (int) b;
		
		if (a < 0) {
			a += 256;
		}
		n1 = a / 16;
		n2 = a % 16;

		res += getChar(n1);
		res += getChar(n2);

		return res;
	}

	/**
	 * Pomoćna metoda koja za dani broj vraća heksadekadsku znamenku zapisanu kao
	 * {@link Character}.
	 * 
	 * @param n vrijednost heksadekadske znamenke u dekadskom zapisu.
	 * @return heksadekadski zapis danog broja.
	 */
	private static char getChar(int n) {
		switch (n) {
		case 0:
			return '0';
		case 1:
			return '1';
		case 2:
			return '2';
		case 3:
			return '3';
		case 4:
			return '4';
		case 5:
			return '5';
		case 6:
			return '6';
		case 7:
			return '7';
		case 8:
			return '8';
		case 9:
			return '9';
		case 10:
			return 'A';
		case 11:
			return 'B';
		case 12:
			return 'C';
		case 13:
			return 'D';
		case 14:
			return 'E';
		case 15:
			return 'F';
		default:
			throw new IllegalArgumentException("Given value has to be >= 0 and < 16, but was " + n + "!");
		}
	}

}
