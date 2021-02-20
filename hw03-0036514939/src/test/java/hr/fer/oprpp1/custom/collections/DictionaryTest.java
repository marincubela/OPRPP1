package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

	@Test
	public void testEmpty() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		assertTrue(dictionary.isEmpty());

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		assertFalse(dictionary.isEmpty());
	}

	@Test
	public void testIsEmptyAfterClear() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		dictionary.clear();

		assertTrue(dictionary.isEmpty());
	}

	@Test
	public void testGetSize() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		assertEquals(0, dictionary.size());

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		assertEquals(2, dictionary.size());
	}

	@Test
	public void testClear() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);
		dictionary.clear();
		assertEquals(0, dictionary.size());

	}

	@Test
	public void testPut() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		assertEquals(1, dictionary.get("First"));
		assertEquals(2, dictionary.get("Second"));
	}

	@Test
	public void testPutOverwrites() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);
		dictionary.put("First", 3);

		assertEquals(3, dictionary.get("First"));
	}

	@Test
	public void testPutValueAndRemove() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		dictionary.remove("First");

		assertNull(dictionary.get("First"));
	}

	@Test
	public void testPutShouldThrowNullPointerException() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		assertThrows(NullPointerException.class, () -> dictionary.put(null, 2));
	}

	@Test
	public void testGetReturnNull() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		assertEquals(null, dictionary.get(null));
		assertEquals(null, dictionary.get("Nitko"));
	}

	@Test
	public void testRemoveGetsOldValue() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		assertEquals(1, dictionary.remove("First"));
		assertEquals(null, dictionary.remove("Nema"));
		assertEquals(null, dictionary.remove(null));
	}

	@Test
	public void testRemove() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();

		dictionary.put("First", 1);
		dictionary.put("Second", 2);

		dictionary.remove("First");

		assertEquals(1, dictionary.size());
	}
}
