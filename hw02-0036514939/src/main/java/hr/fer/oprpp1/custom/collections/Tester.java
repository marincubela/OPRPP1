package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje kojim modeliramo objekte koji primaju neki objekt i ispitaju je li prihvatljiv ili ne.
 * 
 * @author Cubi
 *
 */
public interface Tester {
	/**
	 * Metoda koja provjerava je li dani objekt prihvatljiv ili nije.
	 * @param obj objekt koji testiramo.s
	 * @return <code>true</code> ako je objekt prihvatljiv, <code>false</code> inače.
	 */
	boolean test(Object obj);
}
