package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje predstavlja neku skupinu (kolekciju) objekata.
 * 
 * @author Cubi
 *
 */
public interface Collection {
	/**
	 * Metoda koja vraća <code>true</code> ako ova kolekcija ne sadrži niti jedan
	 * objekt, inače vraća <code>false</code>.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, <code>false</code> inače.
	 */
	public default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Metoda koja vraća broj objekata pohranjenih u ovoj kolekciji.
	 * 
	 * @return broj objekata u kolekciji.
	 */
	int size();

	/**
	 * Metoda koja dodaje dani objekt u kolekciju.
	 * 
	 * @param value objekt koji dodajemo u kolekciju.
	 */
	void add(Object value);

	/**
	 * Metoda koja provjerava postoji li dani objekt u ovoj kolekciji.
	 * 
	 * @param value objekt za koji provjeravamo je li u kolekciji.
	 * @return <code>true</code> ako je objekt u kolekciji, <code>false</code>
	 *         inače.
	 */
	boolean contains(Object value);

	/**
	 * Metoda koja uklanja dani objekt iz kolekcije ukoliko se nalazi u kolekciji.
	 * Ako postoji više jednakih objekata, uklanja se samo jedan.
	 * 
	 * @param value objekt koji treba ukloniti
	 * @return <code>true</code> ako postoji takav objekt i ako je uklonjen,
	 *         <code>false</code> inače.
	 */
	boolean remove(Object value);

	/**
	 * Metoda koja rezervira novo polje veličine kolekcije, popuni ih objektima iz
	 * kolekcije i vraća to polje. Metoda nikad ne vraća <code>null</code>.
	 * 
	 * @return polje objekata iz kolekcije
	 */
	Object[] toArray();

	/**
	 * Metoda koja nad svakim objektom u kolekciji poziva metodu process iz klase
	 * {@link Processor}. Redoslijed objekata je određen {@link ElementsGetter} koji ta kolekcija implementira.
	 * 
	 * @param processor sadrži metodu koja će se izvršiti nad svim objektima
	 *                  kolekcije.
	 */
	default void forEach(Processor processor) {
		ElementsGetter e = this.createElementsGetter();
		
		while(e.hasNextElement()) {
			processor.process(e.getNextElement());
		}
	}

	/**
	 * Metoda koja sve objekte iz dane kolekcije dodaje u ovu kolekciju.
	 * 
	 * @param other kolekcija čiji se objekti dodaju u ovu kolekciju.
	 */
	default void addAll(Collection other) {
		other.forEach((Object value) -> {
			this.add(value);
		});
	}

	/**
	 * Metoda koja uklanja sve objekte iz kolekcije.
	 */
	void clear();
	
	/**
	 * Metoda koja dodaje sve elemente iz dane kolekcije ako ih dani tester prihvaca.
	 * 
	 * @param col kolekcija elemenata koji se dodaju u ovu kolekciju.
	 * @param tester tester koji određuje prihvaća li se neki element ili ne.
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter e = col.createElementsGetter();
		while(e.hasNextElement()) {
			Object value = e.getNextElement();
			if(tester.test(value)) {
				this.add(value);
			}
		}
	}
	
	/**
	 * Metoda za kreiranje objekta tipa {@link ElementsGetter} kojim se može iterirati po kolekciji.
	 * 
	 * @return {@link ElementsGetter} koji može iterirati po ovoj kolekciji.
	 */
	ElementsGetter createElementsGetter();
}
