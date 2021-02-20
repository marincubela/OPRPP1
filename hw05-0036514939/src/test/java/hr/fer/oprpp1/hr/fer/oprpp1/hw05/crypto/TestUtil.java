package hr.fer.oprpp1.hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw05.crypto.Util;

public class TestUtil {
	@Test
	public void testHextobyte() {
		String keyText = "01aE22";
		byte[] expected = {1, -82, 34};
		
		assertArrayEquals(expected, Util.hextobyte(keyText));
		assertArrayEquals(expected, Util.hextobyte(keyText.toLowerCase()));
		assertArrayEquals(expected, Util.hextobyte(keyText.toUpperCase()));	
	}
	
	@Test
	public void testHextobyteEmptyString() {
		String keyText = "";
		byte[] expected = {};
		
		assertArrayEquals(expected, Util.hextobyte(keyText));
	}
	
	@Test
	public void testHextobyteOddString() {
		String keyText = "01aE2";

		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(keyText));		
	}
	
	@Test
	public void testHextobyteInvalidCharacter() {
		String keyText = "01aG23";

		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(keyText));		
	}
	
	@Test
	public void testBytetohex() {
		byte[] bytearray = {1, -82, 34};
		String expected = "01ae22";
		
		assertEquals(expected, Util.bytetohex(bytearray));
	}
	
	@Test
	public void testBytetohexEmptyArray() {
		byte[] bytearray = {};
		String expected = "";
		
		assertEquals(expected, Util.bytetohex(bytearray));
	}
}
