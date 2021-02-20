package hr.fer.oprpp1.java.gui.layouts;

/**
 * Iznimka koja se baca ako dođe do pogreške u radu s {@link CalcLayout}.
 * 
 * @author Cubi
 *
 */
public class CalcLayoutException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Pretpostavljeni konstruktor za klasu {@link CalcLayoutException}.
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Konstruktor za klasu {@link CalcLayoutException} koji prima poruku kao argument.
	 * 
	 * @param msg poruka uz iznimku.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor za klasu {@link CalcLayoutException} koji prima iznimku kao argument.
	 * 
	 * @param e iznimka koja se proslijeđuje dalje.
	 */
	public CalcLayoutException(Exception e) {
		super(e);
	}
}
