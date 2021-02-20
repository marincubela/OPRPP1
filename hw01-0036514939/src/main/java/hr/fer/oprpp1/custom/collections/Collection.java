package hr.fer.oprpp1.custom.collections;

/**
 * Klasa koja predstavlja neku skupinu (kolekciju) objekata.
 * 
 * @author Cubi
 *
 */
public class Collection {

	/**
	 * Pretpostavljeni konstruktor za klasu {@link Collection}.
	 */
	protected Collection() {
		super();
	}

	/**
	 * Metoda koja vraća <code>true</code> ako ova kolekcija ne sadrži niti jedan
	 * objekt, inače vraća <code>false</code>.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, <code>false</code> inače.
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Metoda koja vraća broj objekata pohranjenih u ovoj kolekciji.
	 * 
	 * @return broj objekata u kolekciji.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Metoda koja dodaje dani objekt u kolekciju.
	 * 
	 * @param value objekt koji dodajemo u kolekciju.
	 */
	public void add(Object value) {
		// Ne radi ništa
	}

	/**
	 * Metoda koja provjerava postoji li dani objekt u ovoj kolekciji.
	 * 
	 * @param value objekt za koji provjeravamo je li u kolekciji.
	 * @return <code>true</code> ako je objekt u kolekciji, <code>false</code>
	 *         inače.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Metoda koja uklanja dani objekt iz kolekcije ukoliko se nalazi u kolekciji.
	 * Ako postoji više jednakih objekata, uklanja se samo jedan.
	 * 
	 * @param value objekt koji treba ukloniti
	 * @return <code>true</code> ako postoji takav objekt i ako je uklonjen,
	 *         <code>false</code> inače.
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Metoda koja rezervira novo polje veličine kolekcije, popuni ih objektima iz
	 * kolekcije i vraća to polje. Metoda nikad ne vraća <code>null</code>.
	 * 
	 * @return polje objekata iz kolekcije
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Metoda koja nad svakim objektom u kolekciji poziva metodu process iz klase
	 * {@link Processor}. Redoslijed objekata je neodređen.
	 * 
	 * @param processor sadrži metodu koja će se izvršiti nad svim objektima
	 *                  kolekcije.
	 */
	public void forEach(Processor processor) {
		// Ne radi ništa
	}

	/**
	 * Metoda koja sve objekte iz dane kolekcije dodaje u ovu kolekciju.
	 * 
	 * @param other kolekcija čiji se objekti dodaju u ovu kolekciju.
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Metoda koja uklanja sve objekte iz kolekcije.
	 */
	public void clear() {
		// Ne radi ništa
	}

}
