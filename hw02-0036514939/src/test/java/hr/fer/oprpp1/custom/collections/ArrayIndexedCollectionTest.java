package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void testConstructorWithTwoArgumentsAndDefault() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		arrCol.add("Jedan");
		arrCol.add("Dva");
		arrCol.add("Tri");
		
		ArrayIndexedCollection arrCol2 = new ArrayIndexedCollection(arrCol, 4);
		assertArrayEquals(arrCol.toArray(), arrCol2.toArray());

		ArrayIndexedCollection arrCol3 = new ArrayIndexedCollection(arrCol);
		assertArrayEquals(arrCol.toArray(), arrCol3.toArray());
	}
	
	@Test
	public void testConstructorWithArgumentCollectionAndArgumentCapacity() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(1);
		arrCol.add("Jedan");
		arrCol.add("Dva");
		arrCol.add("Tri");

		ArrayIndexedCollection arrCol3 = new ArrayIndexedCollection(arrCol);
		assertArrayEquals(arrCol.toArray(), arrCol3.toArray());
	}
	
	@Test
	public void testAddGoodValue() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		arrCol.add("Test");
		
		assertEquals("Test", arrCol.get(0));
	}
	
	@Test
	public void testAddShouldThrowNullPointerException() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		
		assertThrows(NullPointerException.class, () -> arrCol.add(null));
	}
	
	@Test
	public void testAddMoreValuesThanCurrentSizeOfArray() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(1);
		
		arrCol.add("Jedan");
		arrCol.add("Dva");
		arrCol.add("Tri");
		String actual = new String("");
		actual = actual + arrCol.get(0) + arrCol.get(1) + arrCol.get(2);
		String expected = new String("JedanDvaTri");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetShouldWork() {
		ArrayIndexedCollection arrCol= new ArrayIndexedCollection();
		
		arrCol.add(0);
		arrCol.add(1);
		
		int expected = 1;
		
		assertEquals(expected, arrCol.get(1));
	}
	
	@Test
	public void testGetShouldThrowIndexOutOfBoundsForPositive() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		
		arrCol.add(0);
		arrCol.add(1);
		
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.get(2));
	}
	
	@Test
	public void testGetShouldThrowIndexOutOfBoundsForNegative() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		
		arrCol.add(0);
		arrCol.add(1);
		
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.get(-3));
	}
	
	@Test
	public void testInsertShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		
		arrCol.add(0);
		arrCol.add(1);
		arrCol.add(2);
		//Umetni u sredinu
		arrCol.insert(1.5, 2);
		Object[] expected = new Object[] {0, 1, 1.5, 2};
		assertArrayEquals(expected, arrCol.toArray());
		//Umetni na kraj
		arrCol.insert(3, 4);
		expected = new Object[] {0, 1, 1.5, 2, 3};
		assertArrayEquals(expected, arrCol.toArray());
		//Umetni na početak
		arrCol.insert(-1, 0);
		expected = new Object[] {-1, 0, 1, 1.5, 2, 3};
		assertArrayEquals(expected, arrCol.toArray());
	}
	
	@Test
	public void testInsertShouldThrowIndexOutOfBounds() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.insert(-1, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.insert(-1, -1));
		
		arrCol.add(0);
		arrCol.add(1);
		arrCol.add(2);
		
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.insert(-1, 4));
	}
	
	@Test
	public void testInsertShouldThrowNullPointerException() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arrCol.insert(null, 0));
		
		arrCol.add(1);
		arrCol.add(2);
		
		assertThrows(NullPointerException.class, () -> arrCol.insert(null, 2));
	}
	
	@Test
	public void testIndexOfShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(2);
		
		assertEquals(0, arrCol.indexOf(1));
		assertEquals(1, arrCol.indexOf(2));
		assertEquals(-1, arrCol.indexOf(null));
		assertEquals(-1, arrCol.indexOf(3));
	}
	
	@Test
	public void testRemoveIndexShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		arrCol.add(4);
		
		arrCol.remove(2);
		assertEquals(3, arrCol.size());
		assertEquals(4, arrCol.get(2));
		arrCol.remove(0);
		assertEquals(2, arrCol.get(0));
	}
	
	@Test
	public void testRemoveIndexShouldThrowIndexOutOfBounds() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		arrCol.add(4);
		
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> arrCol.remove(4));
	}
	
	@Test
	public void testIsEmptyShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection();
		assertEquals(true, arrCol.isEmpty());

		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		assertEquals(false, arrCol.isEmpty());
		
		arrCol.remove(0);
		arrCol.remove(0);
		arrCol.remove(0);
		assertEquals(true, arrCol.isEmpty());
	}
	
	@Test
	public void testSizeShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(2);
		assertEquals(0, arrCol.size());

		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		assertEquals(3, arrCol.size());
		
		arrCol.remove(0);
		arrCol.remove(0);
		arrCol.remove(0);
		assertEquals(0, arrCol.size());
	}
	
	@Test
	public void testContainsShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(2);
		assertEquals(false, arrCol.contains(2));

		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		assertEquals(true, arrCol.contains(2));
		assertEquals(false, arrCol.contains(null));
		
		arrCol.remove(0);
		arrCol.remove(0);
		arrCol.remove(0);
		assertEquals(false, arrCol.contains(2));
	}
	
	@Test
	public void testRemoveBooleanShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(2);
		assertEquals(false, arrCol.remove("Tri"));

		arrCol.add("Jedan");
		arrCol.add("Dva");
		arrCol.add("Tri");
		arrCol.add("Tri");
		assertEquals(false, arrCol.remove(null));
		assertEquals(true, arrCol.remove("Tri"));
		assertEquals(true, arrCol.contains("Tri"));
		
		arrCol.remove(0);
		arrCol.remove(0);
		arrCol.remove(0);
		assertEquals(false, arrCol.remove("Dva"));
	}

	@Test
	public void testToArrayShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(2);

		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		assertArrayEquals(new Object[] {1,  2, 3}, arrCol.toArray());
		
		arrCol.remove(0);
		assertArrayEquals(new Object[] {2, 3}, arrCol.toArray());
		

		arrCol.remove(0);
		arrCol.remove(0);
		assertArrayEquals(new Object[] {}, arrCol.toArray());
	}

	@Test
	public void testClearShouldWork() {
		ArrayIndexedCollection arrCol = new ArrayIndexedCollection(2);

		arrCol.add(1);
		arrCol.add(2);
		arrCol.add(3);
		assertArrayEquals(new Object[] {1,  2, 3}, arrCol.toArray());
		arrCol.clear();
		assertArrayEquals(new Object[] {}, arrCol.toArray());
		assertEquals(0, arrCol.size());		
	}
}
