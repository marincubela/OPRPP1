package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje modelira jedan operator usporedbe. Definira metodu koja prima
 * dva argumenta i vraća <code>true</code> ako zadovoljavaju danu usporedbu,
 * <code>false</code> inače.
 * 
 * @author Cubi
 *
 */
public interface IComparisonOperator {
	/**
	 * Metoda koja vraća <code>true</code> ako dana dva argumenta zadovoljavaju
	 * operator usporedbe, <code>false</code> inače.
	 * 
	 * @param value1 prvi argument.
	 * @param value2 drugi argument.
	 * @return <code>true</code> ako je operator zadovoljen, <code>false</code> inače.
	 */
	public boolean satisfied(String value1, String value2);
}
