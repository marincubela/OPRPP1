package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Iznimka koja predstavlja grešku u izvođenju parsera.
 * @author Cubi
 *
 */
public class SmartScriptParserException extends RuntimeException{
	
	private static final long serialVersionUID = 2232993868781911482L;

	public SmartScriptParserException() {
		super();
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(Exception e) {
		super(e);
	}
}
