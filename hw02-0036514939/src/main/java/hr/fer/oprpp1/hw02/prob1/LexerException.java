package hr.fer.oprpp1.hw02.prob1;

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
}
