package hr.fer.oprpp1.hw04.db;

/**
 * Klasa koja sadrži statičke implementacije pojedinih operatora usporedbe.
 * 
 * @author Cubi
 *
 */
public class ComparisonOperators {
	public static final IComparisonOperator LESS = (value1, value2) -> {
		return (value1.compareTo(value2) < 0) ? true : false;
	};
	
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return (value1.compareTo(value2) <= 0) ? true : false;
	};
	
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return (value1.compareTo(value2) > 0) ? true : false;
	};
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return (value1.compareTo(value2) >= 0) ? true : false;
	};
	
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return (value1.compareTo(value2) == 0) ? true : false;
	};
	
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return (value1.compareTo(value2) != 0) ? true : false;
	};
	
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		if(value2.indexOf('*') >= 0) {
			if(value2.indexOf('*') == 0) {
				return value1.endsWith(value2.substring(1));
			} else if(value2.indexOf('*') == value2.length() - 1) {
				return value1.startsWith(value2.substring(0, value2.length() - 1));
			} else {
				String[] splitted = value2.split("\\*");
				return value1.startsWith(splitted[0]) && value1.endsWith(splitted[1]) && value1.length() >= value2.length() - 1;
			}
		} else {
			return value1.equals(value2);
		}
	};	

}
