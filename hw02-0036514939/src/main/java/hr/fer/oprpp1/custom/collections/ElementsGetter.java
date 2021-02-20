package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje implementiraju klase za lakše iteriranje po elementima.
 * Postoje dvije metode pomoću kojih korisnik doznaje postoje li više elemenata i dobiva sljedeći element
 * 
 * @author Cubi
 *
 */
public interface ElementsGetter {
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
	Object getNextElement();
	
	default void processRemaining(Processor p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
