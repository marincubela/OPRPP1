package hr.fer.oprpp1.custom.collections;

import java.util.Collections;

/**
 * Klasa koja služi kao indeksirana kolekcija elemenata, čija se veličina može
 * mijenjati i koristi dvostruko povezanu listu za pohranu podataka. Nasljeđuje
 * klasu {@link Collections}.
 * 
 * @author Cubi
 *
 */
public class LinkedListIndexedCollection extends Collection {

	private static class ListNode {
		protected ListNode previous;
		protected ListNode next;
		protected Object value;
	}

	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Pretpostavljeni konstruktor za klasu {@link LinkedListIndexedCollection}.
	 * Inicijalizira praznu kolekciju.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
		this.size = 0;
	}

	/**
	 * Konstruktor za klasu {@link LinkedListIndexedCollection} koji prima drugu
	 * kolekciju kao argument. Ova se kolekcija popuni elementima predane kolekcije.
	 * 
	 * @param other kolekcija kojom se inicijano napuni ova kolekcija
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.size = 0;
		this.addAll(other);
	}

	/**
	 * Metoda koja dodaje dani objekt u kolekciju. Ako je dani objekt
	 * <code>null</code> baca se iznimka.
	 * 
	 * @throws NullPointerException baca ako se u kolekciju pokuša dodati null
	 *                              vrijednost
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Kolekcija ne prima null vrijednosti za elemente!");
		}

		ListNode newElement = new ListNode();
		newElement.value = value;

		if (this.first == null) {
			newElement.previous = null;
			newElement.next = null;
			this.first = this.last = newElement;
		} else {
			newElement.previous = this.last;
			newElement.next = null;
			this.last.next = newElement;
			this.last = newElement;
		}

		this.size++;
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

		ListNode result = this.first;
		ListNode result1 = this.first;
		ListNode result2 = this.last;
		int i, j;

		for (i = 0, j = this.size - 1; i < index && j > index; i++, j--) {
			result1 = result1.next;
			result2 = result2.previous;
		}
		
		if(i == index) {
			result = result1;
		} else {
			result = result2;
		}
		return result.value;
	}

	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
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

		ListNode newElement = new ListNode();
		ListNode auxElement = this.first;

		if (position == this.size) {
			this.add(value);
			return;
		} else if (position == 0) {
			newElement.next = this.first;
			newElement.next.previous = newElement;
			this.first = newElement;
			this.size++;
		} else {
			for (int i = 0; i < position; i++) {
				auxElement = auxElement.next;
			}
			
			newElement.next = auxElement;
			newElement.previous = auxElement.previous;
			auxElement.previous.next = newElement;
			auxElement.previous = newElement;
			this.size++;
		}
		newElement.value = value;
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
			ListNode auxElement = this.first;
			for (int i = 0; i < this.size; i++, auxElement = auxElement.next) {
				if (auxElement.value.equals(value)) {
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

		if (this.size == 1) {
			this.first = this.last = null;
		} else if (index == this.size - 1) {
			this.last = this.last.previous;
			this.last.next = null;
		} else if (index == 0) {
			this.first = this.first.next;
			this.first.previous = null;
		} else {
			ListNode auxElement = this.first;
			for (int i = 0; i < index; i++) {
				auxElement = auxElement.next;
			}

			auxElement.previous.next = auxElement.next;
			auxElement.next.previous = auxElement.previous;
		}
		this.size--;
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
		ListNode auxElement = this.first;
		while (auxElement != null) {
			if (auxElement.value.equals(value)) {
				return true;
			}
			auxElement = auxElement.next;
		}

		return false;
	}

	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			this.remove(this.indexOf(value));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[this.size()];
		int index = 0;
		ListNode auxElement = this.first;

		while (auxElement != null) {
			result[index++] = auxElement.value;
			auxElement = auxElement.next;
		}

		return result;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode auxElement = this.first;
		for(int i = 0; i < this.size; i++) {
			processor.process(auxElement.value);
			auxElement = auxElement.next;
		}
	}
}
