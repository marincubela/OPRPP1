package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void lessTest() {
		String param1 = "Iva";
		String[] params = { "Marin", "Andrea", "Ivan", "Iva" };
		
		IComparisonOperator oper = ComparisonOperators.LESS;
		boolean[] result = new boolean[4];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {true, false, true, false}, result);
	}
	
	@Test
	public void lessOrEqualsTest() {
		String param1 = "Iva";
		String[] params = { "Marin", "Andrea", "Ivan", "Iva" };
		
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		boolean[] result = new boolean[4];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {true, false, true, true}, result);
	}
	
	@Test
	public void greaterTest() {
		String param1 = "Iva";
		String[] params = { "Marin", "Andrea", "Ivan", "Iva" };
		
		IComparisonOperator oper = ComparisonOperators.GREATER;
		boolean[] result = new boolean[4];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {false, true, false, false}, result);
	}
	
	@Test
	public void greaterOrEqualsTest() {
		String param1 = "Iva";
		String[] params = { "Marin", "Andrea", "Ivan", "Iva" };
		
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		boolean[] result = new boolean[4];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {false, true, false, true}, result);
	}
	
	@Test
	public void equalsTest() {
		String param1 = "Iva";
		String[] params = { "Marin", "Andrea", "Ivan", "Iva" };
		
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		boolean[] result = new boolean[4];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {false, false, false, true}, result);
	}

	@Test
	public void notEqualsTest() {
		String param1 = "Iva";
		String[] params = { "Marin", "Andrea", "Ivan", "Iva" };
		
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		boolean[] result = new boolean[4];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {true, true, true, false}, result);
	}
	
	@Test
	public void likeTest() {
		String param1 = "AAA";
		String[] params = { "*A", "A*", "an*s", "iva*", "AA*AA" };
		
		IComparisonOperator oper = ComparisonOperators.LIKE;
		boolean[] result = new boolean[5];
		
		for(int i = 0; i < params.length; i++)
			result[i] = oper.satisfied(param1, params[i]);
 		
		assertArrayEquals(new boolean[] {true, true, false, false, false}, result);
	}

}
