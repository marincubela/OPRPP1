package hr.fer.oprpp1.hw04.db;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestParser {
	
	@Test
	public void testIsDirectQuery() {
		String text = "jmbag = \"0000003\"";
		QueryParser parser = new QueryParser(text);
		assertTrue(parser.isDirectQuery());
	}
	
	@Test
	public void testgetQueriedJMBAG() {
		String text = "jmbag = \"0000003\"";
		QueryParser parser = new QueryParser(text);
		assertEquals(parser.getQueriedJMBAG(), "0000003");
	}
	
	@Test
	public void testParser() {
		String text = "firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"";
		QueryParser parser = new QueryParser(text);
		
		assertEquals(parser.getQuery().size(), 4);
		assertEquals("A", parser.getQuery().get(0).getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, parser.getQuery().get(2).getComparisonOperator());
		for(ConditionalExpression ce : parser.getQuery()) {
			System.out.println(ce.getFieldGetter() + " "+ ce.getComparisonOperator() + " " + ce.getStringLiteral());
		}
	}

}