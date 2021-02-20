package hr.fer.oprpp1.hw04.db;

/**
 * Klasa koja modelira izraz usporedbe.
 * 
 * @author Cubi
 *
 */
public class ConditionalExpression {
	private IfieldValueGetter valueGetter;
	private String value;
	private IComparisonOperator oper;
	
	/**
	 * Konstruktor za klasu {@link ConditionalExpression} koji inicijalizira potrebne vrijednosti.
	 * 
	 * @param valueGetter polje iz zapisa koje će se uspoređivati.
	 * @param value string literal s kojim će se uspoređivati.
	 * @param oper operator usporedbe.
	 */
	public ConditionalExpression(IfieldValueGetter valueGetter, String value, IComparisonOperator oper) {
		this.valueGetter = valueGetter;
		this.value = value;
		this.oper = oper;
	}

	/**
	 * Metoda koja vraća polje zapisa koje se koristi u usporedbi.
	 * 
	 * @return polje zapisa koje se koristi u usporedbi.
	 */
	public IfieldValueGetter getFieldGetter() {
		return valueGetter;
	}

	/**
	 * Metoda koja vraća string literal koji se koristi u ovoj usporedbi.
	 * 
	 * @returnstring literal koji se koristi u ovoj usporedbi.
	 */
	public String getStringLiteral() {
		return value;
	}

	/**
	 * Metoda koja vraća operator usporedbe u ovom izrazu.
	 * 
	 * @return operator usporedbe u ovom izrazu.
	 */
	public IComparisonOperator getComparisonOperator() {
		return oper;
	}
	
	
}
