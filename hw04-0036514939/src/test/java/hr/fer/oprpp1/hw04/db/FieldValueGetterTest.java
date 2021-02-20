package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGetterTest {
	
	@Test
	public void firstNameGetterTest() {
		StudentRecord record = new StudentRecord("123", "AAAAA", "BBBBB", 5);
		
		assertEquals("AAAAA", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	public void lastNameGetterTest() {
		StudentRecord record = new StudentRecord("123", "AAAAA", "BBBBB", 5);
		
		assertEquals("BBBBB", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	public void jmabgGetterTest() {
		StudentRecord record = new StudentRecord("123", "AAAAA", "BBBBB", 5);
		
		assertEquals("123", FieldValueGetters.JMBAG.get(record));
	}

}
