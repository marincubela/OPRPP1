package hr.fer.oprpp1.hw05.shell;

/**
 * Iznimka koja se baca ako dođe do pogreške u radu {@link MyShell} programa.
 * 
 * @author Cubi
 *
 */
public class ShellIOException extends RuntimeException {
	private static final long serialVersionUID = -7314086663390870150L;

	/**
	 * Pretpostavljeni konstruktor za klasu {@link ShellIOException}.
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Konstruktor za klasu {@link ShellIOException} koji prima poruku kao argument.
	 * 
	 * @param msg poruka uz iznimku.
	 */
	public ShellIOException(String msg) {
		super(msg);
	}
	
	/**
	 * Konstruktor za klasu {@link ShellIOException} koji prima iznimku kao argument.
	 * 
	 * @param e iznimka koja se proslijeđuje dalje.
	 */
	public ShellIOException(Exception e) {
		super(e);
	}

}
