package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje implementiraju klase za lakše iteriranje po elementima.
 * Postoje dvije metode pomoću kojih korisnik doznaje postoje li više elemenata i dobiva sljedeći element
 * 
 * @author Cubi
 *
 */
public interface ElementsGetter<T> {
	/**
	 * Metoda za provjeravanje postojanja sljedećeg elementa u klasi
	 * 
	 * @return <code>true</code> ako postoji sljedeći element, <code>false</code> inače
	 */
	boolean hasNextElement();
	
	/**
	 * Metoda koja vraća sljedeći element u kolekciji ako takav postoji.
	 * Ako ne postoji više elemenata, trebala bi se baciti iznimka.
	 * 
	 * @return sljedeći element u kolekciji.
	 */
	T getNextElement();
	
	/**
	 * Metoda koja za svaki element poziva procesor p da izvrši neku radnju nad tim elementom.
	 * 
	 * @param p {@link Processor} koji definira radnju koja će se obaviti nad svim elemntima.
	 */
	default void processRemaining(Processor<? super T> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
