package hr.fer.oprpp1.hw04.db;

/**
 * Funkcionalno učelje čije implementacije trebaju provjeriti prihvaća li se
 * dani zapis o studentu ili ne pomoću metode accepts.
 * 
 * @author Cubi
 *
 */
public interface IFilter {
	/**
	 * Metoda koja vraća <code>true</code> ako se zapis prihvaća, <code>false</code>
	 * inače.
	 * 
	 * @param record zapis koji treba prihvatiti ili ne.
	 * @return <code>true</code> ako se zapis prihvaća, <code>false</code> inače.
	 */
	public boolean accepts(StudentRecord record);
}
