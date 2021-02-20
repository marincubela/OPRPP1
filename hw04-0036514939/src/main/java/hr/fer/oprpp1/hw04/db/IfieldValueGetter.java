package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje je odgovorno za dohvatanje određenih polja iz {@link StudentRecord}.
 * 
 * @author Cubi
 *
 */
public interface IfieldValueGetter {
	/**
	 * Metoda koja vraća određeno polje iz danog zapisa o studentu.
	 * 
	 * @param record zapis o studentu iz kojeg izvlačimo polje.
	 * @return traženo polje iz zapisa.
	 */
	public String get(StudentRecord record);
}
