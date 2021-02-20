package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	@Test
	public void shouldNotAccept() {
		var list = new ArrayList<ConditionalExpression>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1234567890", ComparisonOperators.EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "iva", ComparisonOperators.GREATER_OR_EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "istuk", ComparisonOperators.NOT_EQUALS));
			
		QueryFilter q = new QueryFilter(list);
		
		assertFalse(q.accepts(new StudentRecord("1234567890", "iva", "istuk",2)));
	}
	
	@Test
	public void shouldAccept() {
		var list = new ArrayList<ConditionalExpression>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1234567890", ComparisonOperators.EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "iva", ComparisonOperators.GREATER_OR_EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "istuk", ComparisonOperators.NOT_EQUALS));
			
		QueryFilter q = new QueryFilter(list);
		
		assertTrue(q.accepts(new StudentRecord("1234567890", "iva", "qqqqqqq",2)));
	}

}
