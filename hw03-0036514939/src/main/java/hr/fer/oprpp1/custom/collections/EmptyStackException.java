package hr.fer.oprpp1.custom.collections;

/**
 * Iznimka koja se baca ukoliko se pokuša pristupiti nekom elementu sa stoga, a on je prazan.
 * @author Cubi
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = -6076355322985440959L;
	
	/**
	 * Konstruktor za iznimku {@link EmptyStackException}
	 * 
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Pretpostavljeni konstruktor za iznimku {@link EmptyStackException}
	 */
	public EmptyStackException() {
		super();
	}
}
