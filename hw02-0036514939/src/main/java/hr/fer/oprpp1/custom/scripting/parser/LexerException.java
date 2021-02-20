package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Iznimka koja predstavlja grešku u izvođenju leksera.
 * 
 * @author Cubi
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LexerException() {
		super();
	}
	
	public LexerException(String message) {
		super(message);
	}
	
	public LexerException(Exception e) {
		super(e);
	}
}
