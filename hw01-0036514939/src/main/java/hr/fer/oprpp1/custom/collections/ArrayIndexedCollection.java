package hr.fer.oprpp1.custom.collections;

import java.util.Collections;

/**
 * Klasa koja služi kao indeksirana kolekcija elemenata, čija se veličina može mijenjati
 * i koristi polje za pohranu objekata.
 * Nasljeđuje klasu {@link Collections}.
 * @author Cubi
 *
 */
public class ArrayIndexedCollection extends Collection {

	private final static int defaultInitialCapacity = 16;

	private int size;
	private Object[] elements;

	/**
	 * Konstruktor za klasu {@link ArrayIndexedCollection} koji prima dva argumenta: kolekciju i početni kapacitet.
	 * Ova kolekcija se popuni elementima iz dane kolekcije, a početna veličina polja koje se koristi za
	 * pohranu podataka poprima vrijednost drugog argumenta. Ako dana kolekcija ima više elemenata od početnog kapaciteta,
	 * početni kapacitet polja jednak je veličini dane kolekcije. 
	 * Ukoliko se za kolekciju preda <code>null</code>, baca se {@link NullPointerException}.
	 * Početni kapacitet mora biti barem 1, inače se baca {@link IllegalArgumentException}.
	 * 
	 * @param other kolekcija kojom će se popuniti ova kolekcija.
	 * @param initialCapacity početna veličina polja korištenog za pohranu.
	 * @throws NullPointerException baca se ako je dana kolekcija <code>null</code>.
	 * @throws IllegalArgumentException baca se ako je dani početni kapacitet manji od 1.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		super();

		if (other == null) {
			throw new NullPointerException("Predana kolekcija ne smije biti null!");
		}
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Inicijalni kapacitet ne smije biti manji od 1, a bio je " + initialCapacity + "!");
		}
		if (other.size() > initialCapacity) {
			elements = new Object[other.size()];
		} else {
			elements = new Object[initialCapacity];
		}

		this.addAll(other);
	}

	/**
	 * Konstruktor za klasu {@link ArrayIndexedCollection} koji prima samo jendu kolekciju.
	 * Ova kolekcija se popuni elementima iz dane kolekcije, a početna veličina polja koje se koristi za
	 * pohranu podataka jest pretpostavljena vrijednost. Ako dana kolekcija ima više elemenata od početnog kapaciteta,
	 * početni kapacitet polja jednak je veličini dane kolekcije. 
	 * Ukoliko se za kolekciju preda <code>null</code>, baca se {@link NullPointerException}.
	 * 
	 * @param other kolekcija kojom će se popuniti ova kolekcija.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, defaultInitialCapacity);
	}

	/**
	 * Konstruktor za klasu {@link ArrayIndexedCollection} koji prima početni kapacitet polja za pohranu podataka.
	 * Za kolekciju se alocira polje veličine početnog kapaciteta.
	 * Početni kapacitet mora biti barem 1, inače se baca {@link IllegalArgumentException}.
	 * 
	 * @param initialCapacity početna veličina polja korištenog za pohranu.
	 * @throws IllegalArgumentException baca se ako je dani početni kapacitet manji od 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();

		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Inicijalni kapacitet ne smije biti manji od 1, a bio je " + initialCapacity + "!");
		}

		elements = new Object[initialCapacity];
		this.size = 0;
	}

	/**
	 * Pretpostavljeni konstruktor za klasu {@link ArrayIndexedCollection}.
	 * Alocira polje za pohranu pretpostavljene početne veličine.
	 */
	public ArrayIndexedCollection() {
		this(defaultInitialCapacity);
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}

	/**
	 * Metoda koja dodaje dani objekt u kolekciju.
	 * Ako je dani objekt <code>null</code> baca se iznimka.
	 * 	@throws NullPointerException baca ako se u kolekciju pokuša dodati null vrijednost
	 */
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Kolekcija ne prima null vrijednosti za elemente!");
		}

		if (this.elements.length <= this.size) {
			Object[] newElements = new Object[2 * this.size];
			for (int i = 0; i < this.elements.length; i++) {
				newElements[i] = this.elements[i];
			}
			this.elements = newElements;
		}

		elements[this.size++] = value;
	}

	/**
	 * Metoda koja vraća element koji se nalazi na danom mjestu u kolekciji ako
	 * takav postoji. Ukoliko ne postoji, metoda baca iznimku.
	 * 
	 * @param index pozicija elementa koji se traži u kolekciji.
	 * @return element kolekcije na traženoj poziciji.
	 * @throws IndexOutOfBoundsException iznimka za dana mjesta koja ne postoje u
	 *                                   kolekciji.
	 */
	public Object get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Ne postoji element s indexom " + index + "u ovoj kolekciji!");
		}
		return this.elements[index];
	}

	/**
	 * Metoda koja dodaje dani objekt u kolekciju na danu poziciju. Sve ostale
	 * vrijednosti ostaju sačuvane, a elementi koji su se nalazi na poziciji ili
	 * poslije nje su pomaknuti za jedno mjesto. Valjane vrijednosti za poziciju su
	 * svi brojevi između 0 i veličine kolekcije. Ako metoda primi nevaljanu
	 * vrijednost, baca se iznimka {@link IndexOutOfBoundsException}.
	 * 
	 * @param value    objekt koji dodajemo u kolekciju,
	 * @param position pozicija na kojoj će objekt biti dodan.
	 * @throws IndexOutOfBoundsException baca se za neispravne vrijednosti pozicije.
	 * @throws NullPointerException      baca se ako je predani objekt
	 *                                   <code>null</code>.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Nije moguće dodati element na poziciju " + position
					+ ", veličina kolekcije je " + this.size + "!");
		}
		if (value == null) {
			throw new NullPointerException("Kolekcija ne prima null vrijednosti za elemente!");
		}
		// Za dodavanje na kraj kolekcije već imamo metodu.
		if (position == this.size) {
			this.add(value);
			return;
		}

		this.add(this.elements[this.size - 1]); // Ako je potrebno alocirati dodatnu memoriju, metoda add će se
												// pobrinuti za to.

		for (int i = this.size - 1; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
		}

		this.elements[position] = value;
	}

	/**
	 * Metoda traži predani objekt u kolekciji i vraća poziciju prvog objekta koji
	 * je jednak danom objektu. Ako kolekcija ne sadrži dani objekt, metoda vraća
	 * -1. U to se podrazumijeva i ako je predani objekt <code>null</code>.
	 * 
	 * @param value objekt čija se pozicija traži u kolekciji.
	 * @return pozicija prvog objekta koji je jednak danom objektu, ako takvog nema
	 *         onda -1.
	 */
	public int indexOf(Object value) {
		if (value != null) {
			for (int i = 0; i < this.size; i++) {
				if (this.elements[i].equals(value)) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * Metoda koja uklanja element na danoj poziciji iz kolekcije. Svi ostali
	 * elementi se pomjeraju na jednu poziciju ispred. Ako je dana pozicija
	 * neispravna, baca iznimku {@link IndexOutOfBoundsException}.
	 * 
	 * @param index pozicija elementa koji se uklanja iz kolekcije
	 * @throws IndexOutOfBoundsException baca se u slučaju neispravne pozicije
	 */
	public void remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Ne postoji element s indexom " + index + "u ovoj kolekciji!");
		}

		for (int i = index; i < this.size - 1; i++) {
			this.elements[i] = this.elements[i + 1];
		}

		this.elements[--this.size] = null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		if(this.contains(value)) {
			this.remove(this.indexOf(value));
			return true;
		} else {
			return false;			
		}
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			array[i] = this.elements[i];
		}
		
		return array;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
}
