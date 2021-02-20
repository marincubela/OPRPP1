package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testConstructorWithTwoArguments() {
		ArrayIndexedCollection linCol = new ArrayIndexedCollection();
		linCol.add("Jedan");
		linCol.add("Dva");
		linCol.add("Tri");
		
		ArrayIndexedCollection linCol2 = new ArrayIndexedCollection(linCol);
		assertArrayEquals(linCol.toArray(), linCol2.toArray());
	}

	@Test
	public void testAddGoodValue() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		linCol.add("Test");

		assertEquals("Test", linCol.get(0));

		linCol.add("Jedan");
		linCol.add("Dva");
		linCol.add("Tri");

		String actual = new String("");
		actual = actual + linCol.get(0) + linCol.get(1) + linCol.get(2) + linCol.get(3);
		String expected = new String("TestJedanDvaTri");

		assertEquals(expected, actual);
	}

	@Test
	public void testAddShouldThrowNullPointerException() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		assertThrows(NullPointerException.class, () -> linCol.add(null));
	}

	@Test
	public void testGetShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		linCol.add(0);
		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		linCol.add(4);

		assertEquals(1, linCol.get(1));
		assertEquals(3, linCol.get(3));
		
	}

	@Test
	public void testGetShouldThrowIndexOutOfBoundsForPositive() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		linCol.add(0);
		linCol.add(1);

		assertThrows(IndexOutOfBoundsException.class, () -> linCol.get(2));
	}

	@Test
	public void testGetShouldThrowIndexOutOfBoundsForNegative() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		linCol.add(0);
		linCol.add(1);

		assertThrows(IndexOutOfBoundsException.class, () -> linCol.get(-3));
	}

	@Test
	public void testInsertShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		// Umetni na početak prazne kolekcije
		linCol.insert(0, 0);
		Object[] expected = new Object[] {0};
		assertArrayEquals(expected, linCol.toArray());
		
		linCol.add(1);
		linCol.add(2);
		// Umetni u sredinu
		linCol.insert(1.5, 2);
		expected = new Object[] { 0, 1, 1.5, 2 };
		assertArrayEquals(expected, linCol.toArray());
		
		// Umetni na kraj
		linCol.insert(3, 4);
		expected = new Object[] { 0, 1, 1.5, 2, 3 };
		assertArrayEquals(expected, linCol.toArray());
		
		// Umetni na početak
		linCol.insert(-1, 0);
		expected = new Object[] { -1, 0, 1, 1.5, 2, 3 };
		assertArrayEquals(expected, linCol.toArray());
	}

	@Test
	public void testInsertShouldThrowIndexOutOfBounds() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		assertThrows(IndexOutOfBoundsException.class, () -> linCol.insert(-1, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> linCol.insert(-1, -1));

		linCol.add(0);
		linCol.add(1);
		linCol.add(2);

		assertThrows(IndexOutOfBoundsException.class, () -> linCol.insert(-1, 4));
	}

	@Test
	public void testInsertShouldThrowNullPointerException() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> linCol.insert(null, 0));

		linCol.add(1);
		linCol.add(2);

		assertThrows(NullPointerException.class, () -> linCol.insert(null, 2));
	}

	@Test
	public void testIndexOfShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		linCol.add(1);
		linCol.add(2);
		linCol.add(2);

		assertEquals(0, linCol.indexOf(1));
		assertEquals(1, linCol.indexOf(2));
		assertEquals(-1, linCol.indexOf(null));
		assertEquals(-1, linCol.indexOf(3));
	}

	@Test
	public void testRemoveIndexShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		
		linCol.add(1);
		linCol.remove(0);
		assertArrayEquals(new Object[] {}, linCol.toArray());
		assertEquals(0, linCol.size());
		
		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		linCol.add(4);

		//Izbaci 3 iz kolekcije, ostaju 1, 2, 4
		linCol.remove(2);
		assertEquals(3, linCol.size());
		assertEquals(4, linCol.get(2));
		
		//Izbaci 1 iz kolekcije, ostaju 2, 4
		linCol.remove(0);
		assertEquals(2, linCol.get(0));
		
		//Izbaci 4 iz kolekcije, ostaje 2
		linCol.remove(1);
		assertEquals(1, linCol.size());
		assertArrayEquals(new Object[] {2}, linCol.toArray());
	}

	@Test
	public void testRemoveIndexShouldThrowIndexOutOfBounds() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		linCol.add(4);

		assertThrows(IndexOutOfBoundsException.class, () -> linCol.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> linCol.remove(4));
	}

	@Test
	public void testIsEmptyShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		assertEquals(true, linCol.isEmpty());

		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		assertEquals(false, linCol.isEmpty());

		linCol.remove(0);
		linCol.remove(0);
		linCol.remove(0);
		assertEquals(true, linCol.isEmpty());
	}

	@Test
	public void testSizeShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		assertEquals(0, linCol.size());

		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		assertEquals(3, linCol.size());

		linCol.remove(0);
		linCol.remove(0);
		linCol.remove(0);
		assertEquals(0, linCol.size());
	}

	@Test
	public void testContainsShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		assertEquals(false, linCol.contains(2));

		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		assertEquals(true, linCol.contains(3));
		assertEquals(true, linCol.contains(1));
		assertEquals(false, linCol.contains(null));

		linCol.remove(0);
		linCol.remove(0);
		linCol.remove(0);
		assertEquals(false, linCol.contains(2));
	}

	@Test
	public void testRemoveBooleanShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();
		assertEquals(false, linCol.remove("Tri"));

		linCol.add("Jedan");
		linCol.add("Dva");
		linCol.add("Tri");
		linCol.add("Tri");
		assertEquals(false, linCol.remove(null));
		assertEquals(true, linCol.remove("Tri"));
		assertEquals(true, linCol.contains("Tri"));

		linCol.remove(0);
		linCol.remove(0);
		linCol.remove(0);
		assertEquals(false, linCol.remove("Dva"));
	}

	@Test
	public void testToArrayShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		assertArrayEquals(new Object[] { 1, 2, 3 }, linCol.toArray());

		linCol.remove(0);
		assertArrayEquals(new Object[] { 2, 3 }, linCol.toArray());

		linCol.remove(0);
		linCol.remove(0);
		assertArrayEquals(new Object[] {}, linCol.toArray());
	}

	@Test
	public void testClearShouldWork() {
		LinkedListIndexedCollection linCol = new LinkedListIndexedCollection();

		linCol.add(1);
		linCol.add(2);
		linCol.add(3);
		assertArrayEquals(new Object[] { 1, 2, 3 }, linCol.toArray());
		linCol.clear();
		assertArrayEquals(new Object[] {}, linCol.toArray());
		assertEquals(0, linCol.size());
	}

}
