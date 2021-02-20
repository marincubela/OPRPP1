package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Klasa koja služi kao indeksirana kolekcija elemenata, čija se veličina može mijenjati
 * i koristi polje za pohranu objekata.
 * Implementira klasu {@link Collection}.
 * @author Cubi
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {

	private final static int defaultInitialCapacity = 16;

	private int size;
	private T[] elements;
	private long modificationCount;

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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> other, int initialCapacity) {
		super();

		if (other == null) {
			throw new NullPointerException("Predana kolekcija ne smije biti null!");
		}
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Inicijalni kapacitet ne smije biti manji od 1, a bio je " + initialCapacity + "!");
		}
		if (other.size() > initialCapacity) {
			elements = (T[]) new Object[other.size()];
		} else {
			elements = (T[]) new Object[initialCapacity];
		}
		
		this.modificationCount = 0;

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
	public ArrayIndexedCollection(Collection<T> other) {
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		super();

		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Inicijalni kapacitet ne smije biti manji od 1, a bio je " + initialCapacity + "!");
		}

		elements = (T[]) new Object[initialCapacity];
		this.modificationCount = this.size = 0;
	}

	/**
	 * Pretpostavljeni konstruktor za klasu {@link ArrayIndexedCollection}.
	 * Alocira polje za pohranu pretpostavljene početne veličine.
	 */
	public ArrayIndexedCollection() {
		this(defaultInitialCapacity);
	}

	/**
	 * Metoda koja dodaje dani objekt u kolekciju.
	 * Ako je dani objekt <code>null</code> baca se iznimka.
	 * 	@throws NullPointerException baca ako se u kolekciju pokuša dodati null vrijednost
	 */
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException("Kolekcija ne prima null vrijednosti za elemente!");
		}

		if (this.elements.length <= this.size) {
			@SuppressWarnings("unchecked")
			T[] newElements = (T[]) new Object[2 * this.size];
			for (int i = 0; i < this.elements.length; i++) {
				newElements[i] = this.elements[i];
			}
			this.elements = newElements;
			this.modificationCount++;
		}

		elements[this.size++] = value;
		this.modificationCount++;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Ne postoji element s indexom " + index + "u ovoj kolekciji!");
		}
		return this.elements[index];
	}

	@Override
	public void insert(T value, int position) {
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
			this.modificationCount++;
			return;
		}

		this.add(this.elements[this.size - 1]); // Ako je potrebno alocirati dodatnu memoriju, metoda add će se
												// pobrinuti za to.

		for (int i = this.size - 1; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
			this.modificationCount++;
		}

		this.elements[position] = value;
		this.modificationCount++;
	}

	@Override
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

	@Override
	public void remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Ne postoji element s indexom " + index + "u ovoj kolekciji!");
		}

		for (int i = index; i < this.size - 1; i++) {
			this.elements[i] = this.elements[i + 1];
			this.modificationCount++;
		}

		this.elements[--this.size] = null;
		this.modificationCount++;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object value) {
		if(this.contains(value)) {
			this.remove(this.indexOf((T) value));
			this.modificationCount++;
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
			this.modificationCount++;
		}
		this.size = 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayIndexedCollection<?> other = (ArrayIndexedCollection<?>) obj;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public ElementsGetter<T> createElementsGetter() {
		return new ArrayIndexedElementsGetter<T>(this);
	}
	
	private static class ArrayIndexedElementsGetter<E> implements ElementsGetter<E> {
		private int index;
		private ArrayIndexedCollection<E> col;
		private long savedModificationCount;
		
		private ArrayIndexedElementsGetter(ArrayIndexedCollection<E> col) {
			index = 0;
			this.col = col;
			this.savedModificationCount = col.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(this.savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Polje je strukturno mijenjano! Onemogućen je daljnji pristup elementima!");
			}
			
			if(index < col.size()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public E getNextElement() {
			if(this.savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Polje je strukturno mijenjano! Onemogućen je daljnji pristup elementima!");
			}
			if(index < col.size()) {
				return col.get(index++);
			} else {
				throw new NoSuchElementException("Nema više elemenata u kolekciji kojima nije pristupljeno preko ovog gettera!");
			}
		}
	}
}
