package hr.fer.oprpp1.hw04.db;

public class QueryParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public QueryParserException() {
		super();
	}
	
	public QueryParserException(String message) {
		super(message);
	}
}
