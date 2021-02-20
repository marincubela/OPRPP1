package hr.fer.oprpp1.hw05.crypto;

/**
 * Pomoćna klasa koja pruža metode za pretvaranje {@link String} u polje {@link Byte} i obratno.
 * @author Cubi
 *
 */
public class Util {
	/**
	 * Metoda koja pretvara dani {@link String} koji predstavlja heksadekadski zapis bajtova u polje bajtova. 
	 * 
	 * @param keyText string koji treba pretvoriti u polje bajtova.
	 * @return polje bajtova dobiveno iz datog stringa.
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException(
					"Length of hexadecimal string must be even, but was " + keyText.length() + "!");
		}

		byte[] bytearray = new byte[keyText.length() / 2];

		int i = 0;
		int res;

		while (i + 2 <= keyText.length()) {
			char c1 = keyText.charAt(i);
			char c2 = keyText.charAt(i + 1);

			byte n1 = getValue(c1);
			byte n2 = getValue(c2);
			
			res =  n1 * 16 + n2;
			if(res > 127) {
				res -= 256;
			}
			bytearray[i / 2] = (byte) res;
			i += 2;
		}

		return bytearray;
	}

	/**
	 * Pomoćna metoda koja iz zadanog znaka vraća bajt koji predstavlja.
	 * 
	 * @param c znak koji se pretvara u bajt.
	 * @return bajt izračunat iz znaka.
	 */
	private static byte getValue(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
		case 'A':
			return 10;
		case 'b':
		case 'B':
			return 11;
		case 'c':
		case 'C':
			return 12;
		case 'd':
		case 'D':
			return 13;
		case 'e':
		case 'E':			
			return 14;
		case 'f':
		case 'F':
			return 15;
		default:
			throw new IllegalArgumentException(
					"Hexadecimal string has invalid characters. Valid ones are 0-9, a-f, A-F, but was " + c + "!");
		}
	}

	/**
	 * Metoda koja pretvara polje bajtova u heksadekadski zapis pomoću stringa.
	 * 
	 * @param bytearray polje bajtova koje se pretvara.
	 * @return heksadekadsi prikaz polja bajtova.
	 */
	public static String bytetohex(byte[] bytearray) {
		String res = new String("");
		int n1, n2, a;
		for(byte b : bytearray) {
			a = (int) b;
			if(a < 0) {
				a += 256;
			}
			n1 = a / 16;
			n2 = a % 16;
			
			res += getChar(n1);
			res += getChar(n2);
		}
		return res;
	}

	/**
	 * Pomoćna metoda koja za dani broj vraća heksadekadsku znamenku zapisanu kao {@link Character}.
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
			return 'a';
		case 11:
			return 'b';
		case 12:
			return 'c';
		case 13:
			return 'd';
		case 14:			
			return 'e';
		case 15:
			return 'f';
		default:
			throw new IllegalArgumentException(
					"Given value has to be >= 0 and < 16, but was " + n + "!");
		}
	}
}
