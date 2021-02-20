package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashTableTest {
    @Test
    public void testHashTablePutsValues() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertEquals(2, testTable.get("Kristina"));
        assertEquals(5, testTable.get("Ivana"));
        assertEquals(5, testTable.size());
    }

    @Test
    public void testPutShouldThrowNullPointerException() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);
        
        assertThrows(NullPointerException.class, () -> testTable.put(null, 2));
    }
    
    @Test
    public void testHashTableGet() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);
        
        assertEquals(null, testTable.get(null));
        assertEquals(null, testTable.get("Nitko"));
    }
    
    @Test
    public void testHashTableSize() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        assertEquals(0, testTable.size());
        
        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        assertEquals(3, testTable.size());
        
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);
        assertEquals(5, testTable.size());
    }    

    @Test
    public void testContainsKey() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertTrue(testTable.containsKey("Kristina"));
        assertTrue(testTable.containsKey("Ivana"));
        assertTrue(testTable.containsKey("Ante"));
        assertTrue(testTable.containsKey("Jasna"));
        assertTrue(testTable.containsKey("Josip"));
        assertFalse(testTable.containsKey("Nitko"));
        assertFalse(testTable.containsKey(null));
    }

    @Test
    public void testContainsValue() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", null);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertTrue(testTable.containsValue(100));
        assertTrue(testTable.containsValue(2));
        assertTrue(testTable.containsValue(5));
        assertTrue(testTable.containsValue(null));
    }

    @Test
    public void testRemoveElement() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        testTable.remove("Ivana");
        assertFalse(testTable.containsKey("Ivana"));
        testTable.put("Ivana", 5);
        
        testTable.remove("Jasna");
        assertFalse(testTable.containsKey("Jasna"));
        testTable.put("Jasna", 2);
        
        testTable.remove("Kristina");
        assertFalse(testTable.containsKey("Kristina"));
        testTable.put("Kristina", 2);
        
        testTable.remove("Jasna");
        assertFalse(testTable.containsKey("Jasna"));
        testTable.put("Jasna", 2);        
    }
    
    @Test
    public void testHashTableRemoveNull() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);
        
        assertEquals(null, testTable.remove(null));
        assertEquals(null, testTable.remove("Nitko"));
    	
    }
    
    @Test
    public void testHashTableIsEmpty() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        assertTrue(testTable.isEmpty());
        
        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        assertFalse(testTable.isEmpty());
        
        testTable.remove("Ante");
        testTable.remove("Jasna");
        testTable.remove("Ivana");
        assertTrue(testTable.isEmpty());
    }
    
    @Test
    public void testHashTableToString() {
    	SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);
    	
    	testTable.put("Ivana", 2);
    	testTable.put("Ante", 2);
    	testTable.put("Jasna", 2);
    	testTable.put("Ivana", 5); // overwrites old grade for Ivana
    	System.out.println(testTable);
    	
    	assertEquals("[Ante=2, Ivana=5, Jasna=2]", testTable.toString());
    }
    
    @Test
    public void testHashTableClear() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);
        
        testTable.clear();
        assertTrue(testTable.isEmpty());
    }
    
    @Test
    public void testHashTableIteratorHasNext() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        
        Iterator<SimpleHashTable.TableEntry<String, Integer>> it = testTable.iterator();
        
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        it.next();
        it.next();
        it.next();
        assertFalse(it.hasNext());
        assertFalse(it.hasNext());
        assertFalse(it.hasNext());
        assertFalse(it.hasNext());
    }

    @Test
    public void testHashtableIteratorNextThrowsException() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);

        Iterator<SimpleHashTable.TableEntry<String, Integer>> it = testTable.iterator();

        it.next();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    public void testHashtableIteratorInForEach() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        StringBuilder result = new StringBuilder();

        for (var element : testTable) {
            result.append(element.getKey()).append(element.getValue());
        }

        assertEquals("Josip100Ante2Ivana5Jasna2Kristina2", result.toString());
    }
    
    @Test
    public void testHashTableIteratorRemove() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        int i = 0;
        var it = testTable.iterator();
        
        while(it.hasNext()) {
        	String str = it.next().getKey();
        	i++;
        	if(str.equals("Ivana")) {
        		it.remove();
        	} 
        }
        assertEquals(3, i);
    }

    @Test
    public void testHashtableIteratorRemoveValid() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        var it = testTable.iterator();

        while (it.hasNext()) {
            if (it.next().getKey().equals("Ivana"))
                it.remove();
        }
        assertFalse(testTable.containsKey("Ivana"));
    }
    
    @Test
    public void testHashtableIteratorConcurrentModificationError() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);

        var it = testTable.iterator();

        it.next();
        testTable.put("Lucija", 2);

        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    public void testHashtableIteratorRemoveCalledTwiceThrowsException() {
        SimpleHashTable<String, Integer> testTable = new SimpleHashTable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        var it = testTable.iterator();


        assertThrows(IllegalStateException.class, () -> {
            while (it.hasNext()) {
                if (it.next().getKey().equals("Ivana")) {
                    it.remove();
                    it.remove();
                }
            }
        });
    }

    @Test
    public void testHashtableDoubleIterator() {
        // create collection:
        SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        StringBuilder sb = new StringBuilder();
        for (SimpleHashTable.TableEntry<String, Integer> pair : examMarks) {
            sb.append(String.format("%s => %d\n", pair.getKey(), pair.getValue()));
        }

        assertEquals("""
                Ante => 2
                Ivana => 5
                Jasna => 2
                Kristina => 5
                """,
                sb.toString());
    }
}
