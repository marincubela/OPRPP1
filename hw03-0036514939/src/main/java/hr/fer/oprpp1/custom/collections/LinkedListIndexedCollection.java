package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Klasa koja služi kao indeksirana kolekcija elemenata, čija se veličina može
 * mijenjati i koristi dvostruko povezanu listu za pohranu podataka. Implementira
 * klasu {@link Collection}.
 * 
 * @author Cubi
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	private static class ListNode<E> {
		protected ListNode<E> previous;
		protected ListNode<E> next;
		protected E value;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((previous == null) ? 0 : previous.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
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
			ListNode<?> other = (ListNode<?>) obj;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			if (previous == null) {
				if (other.previous != null)
					return false;
			} else if (!previous.equals(other.previous))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
		
		
	}

	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount;

	/**
	 * Pretpostavljeni konstruktor za klasu {@link LinkedListIndexedCollection}.
	 * Inicijalizira praznu kolekciju.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * Konstruktor za klasu {@link LinkedListIndexedCollection} koji prima drugu
	 * kolekciju kao argument. Ova se kolekcija popuni elementima predane kolekcije.
	 * 
	 * @param other kolekcija kojom se inicijano napuni ova kolekcija
	 */
	public LinkedListIndexedCollection(Collection<T> other) {
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
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException("Kolekcija ne prima null vrijednosti za elemente!");
		}

		ListNode<T> newElement = new ListNode<>();
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

		this.modificationCount++;
		this.size++;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Ne postoji element s indexom " + index + "u ovoj kolekciji!");
		}

		ListNode<T> result = this.first;
		ListNode<T> result1 = this.first;
		ListNode<T> result2 = this.last;
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
		this.modificationCount++;
		this.size = 0;
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

		ListNode<T> newElement = new ListNode<>();
		ListNode<T> auxElement = this.first;

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
		
		this.modificationCount++;
		newElement.value = value;
	}

	@Override
	public int indexOf(Object value) {
		if (value != null) {
			ListNode<T> auxElement = this.first;
			for (int i = 0; i < this.size; i++, auxElement = auxElement.next) {
				if (auxElement.value.equals(value)) {
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

		if (this.size == 1) {
			this.first = this.last = null;
		} else if (index == this.size - 1) {
			this.last = this.last.previous;
			this.last.next = null;
		} else if (index == 0) {
			this.first = this.first.next;
			this.first.previous = null;
		} else {
			ListNode<T> auxElement = this.first;
			for (int i = 0; i < index; i++) {
				auxElement = auxElement.next;
				this.modificationCount++;
			}

			auxElement.previous.next = auxElement.next;
			auxElement.next.previous = auxElement.previous;
		}
		
		this.modificationCount++;
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
		ListNode<T> auxElement = this.first;
		while (auxElement != null) {
			if (auxElement.value.equals(value)) {
				return true;
			}
			auxElement = auxElement.next;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			this.remove(this.indexOf((T) value));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[this.size()];
		int index = 0;
		ListNode<T> auxElement = this.first;

		while (auxElement != null) {
			result[index++] = auxElement.value;
			auxElement = auxElement.next;
		}

		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
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
		LinkedListIndexedCollection<?> other = (LinkedListIndexedCollection<?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		return true;
	}

	public ElementsGetter<T> createElementsGetter() {
		return new LinkedListIndexedElementsGetter<T>(this);
	}
	
	/**
	 * Privatna statička klasa koja implementira sučelje {@link ElementsGetter} kako bi se kroz ovu kolekciju moglo iterirati.
	 * 
	 * @author Cubi
	 *
	 */
	private static class LinkedListIndexedElementsGetter<E> implements ElementsGetter<E> {
		private ListNode<E> node;
		private LinkedListIndexedCollection<E> col;
		private long savedModificationCount;
		
		/**
		 * Konstruktor za klasu {@link LinkedListIndexedElementsGetter} koji prima referencu na kolekciju po kojoj će se iterirati
		 *  i inicijalizira ju.
		 * @param col kolekcija po kojoj se iterira.
		 */
		private LinkedListIndexedElementsGetter(LinkedListIndexedCollection<E> col) {
			this.col = col;
			node = col.first;
			this.savedModificationCount = col.modificationCount;
		}
		
		/**
		 * Metoda koja vraća <code>true</code> ako postoji još elemenata u kolekciji koji nisu dohvaćeni pomoću
		 * ove instance klase{@link LinkedListIndexedElementsGetter}, <code>false</code> inače.
		 * 
		 * @return <code>true</code> ako postoji još elemenata u kolekciji, <code>false</code> inače
		 */
		@Override
		public boolean hasNextElement() {
			if(this.savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Polje je strukturno mijenjano! Onemogućen je daljnji pristup elementima!");
			}
			
			if(node != null) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Metoda koja vraća sljedeći element u kolekciji pomoću klase {@link LinkedListIndexedElementsGetter}.
		 */
		@Override
		public E getNextElement() {
			if(this.savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Polje je strukturno mijenjano! Onemogućen je daljnji pristup elementima!");
			}
			if(node == null) {
				throw new NoSuchElementException("Nema više elemenata u kolekciji kojima nije pristupljeno preko ovog gettera!");
			}
			E v = node.value;
			node = node.next;
			return v;
		}
		
	}
}
