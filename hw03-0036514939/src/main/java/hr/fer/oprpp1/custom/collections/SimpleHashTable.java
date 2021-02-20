package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Klasa koja modelira tablicu s ključevima i pridruženim vrijednostima ključa. 
 * Uređeni parovi ključa i vrijednosti se spremaju pomoću hash vrijednosti ključa za brže obavljanje operacija nad parovima.
 * 
 * @author Cubi
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */
public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K, V>> {

	TableEntry<K, V>[] table;
	int size;
	private int modificationCount;

	/**
	 * Privatni statički razred koji modelira par ključa i vrijednosti pohranjene za
	 * taj ključ. Ključ ne smije biti <code>null</code> dok vrijednost vezana za taj
	 * ključ može.
	 * 
	 * @author Cubi
	 *
	 * @param <K>    tip ključa.
	 * @param <V>tip vrijednosti.
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}

		/**
		 * Metoda koja vraća vrijednost koja je pohranjena za ovaj ključ.
		 * 
		 * @return vrijednost koja je pohranjena za ovaj ključ.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Metoda koja postavlja vrijednost povezanu za ovaj ključ na danu vrijednost.
		 * 
		 * @param value nova vrijednost koja će biti pohranjena za ovaj ključ.
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Metoda koja vraća vrijednost ključa ovog para.
		 * 
		 * @return vrijednost ključa ovog para.
		 */
		public K getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry<K, V> other = (TableEntry<K, V>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * Pretpostavljeni konstruktor za klasu {@link SimpleHashTable} s početnim
	 * kapacitetom 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable() {
		this.table = (TableEntry<K, V>[]) new TableEntry[16];
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * Konstruktor za klasu {@link SimpleHashTable} koji inicijalizira početnu
	 * tablicu na danu vrijednost. Dana vrijednost mora biti potencija broja 2, a
	 * ako nije za početnu vrijedost se uzima prva veća potencija broja 2.
	 * 
	 * @param capacity inicijalni kapacitet tablice.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int capacity) {
		// Pocetna velicina polja ce biti 2^(gornje cijelo od logˇ2(capacity) )
		int initialCapacity = (int) Math.pow(2., Math.ceil(Math.log(capacity) / Math.log(2)));

		this.table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * Metoda koja dodaje novi par u tablicu ako već ne postoji isti ključ i tada
	 * vraća <code>null</code>. Ako postoji onda postavlja vrijednost za taj ključ
	 * na danu vrijednost i vraća staru vrijednost koja je bila vezana za taj ključ.
	 * Vrijednost samog ključa ne smije biti null.
	 * 
	 * @param key   ključ kojem se pokušava promijeniti vrijednost ili ga dodati ako
	 *              ne postoji.
	 * @param value vrijednost koja će biti dodijeljena danom ključu.
	 * @return staru vrijednost koja je bila povezana s ovim ključem.
	 * @throws NullPointerException baca se ako je dani ključ <code>null</code>.
	 */
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Vrijednost ključa ne može biti null!");
		}

		// Provjera za tablicu
		if (1. * this.size / this.table.length >= 3. / 4.) {
			expandTable();
		}

		TableEntry<K, V> newEntry = new TableEntry<K, V>(key, value);
		int index = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> entry = this.table[index];

		while (entry != null) {
			if (entry.getKey().equals(key)) {
				V oldValue = entry.getValue();
				entry.setValue(value);
				return oldValue;
			}
			entry = entry.next;
		}

		entry = this.table[index];

		if (entry == null) {
			this.table[index] = newEntry;
		} else {
			while (entry.next != null) {
				entry = entry.next;
			}
			entry.next = newEntry;
		}

		this.size++;
		this.modificationCount++;
		return null;
	}

	/**
	 * Metoda koja vraća vrijednost povezanu s danim ključem ako postoji. Ako ne,
	 * vraća <code>null</code>.
	 * 
	 * @param key ključ čija se uparena vrijednost traži.
	 * @return vrijednost koja je vezana za dani ključ.
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		TableEntry<K, V> entry = this.table[Math.abs(key.hashCode() % this.table.length)];

		while (entry != null) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
			entry = entry.next;
		}

		return null;
	}

	/**
	 * Metoda koja vraća broj pohranjenih parova ključ, vrijednost za ovu tablicu.
	 * 
	 * @return broj parova u tablici.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Metoda koja vraća <code>true</code> ako postoji dani ključ u tablici,
	 * <code>false</code> inače.
	 * 
	 * @param key vrijednost ključa koja se traži u tablici.
	 * @return <code>true</code> ako postoji dani ključ u tablici,
	 *         <code>false</code> inače.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		TableEntry<K, V> entry = this.table[Math.abs(key.hashCode() % this.table.length)];

		while (entry != null) {
			if (entry.getKey().equals(key)) {
				return true;
			}
			entry = entry.next;
		}

		return false;
	}

	/**
	 * Metoda koja vraća <code>true</code> ako postoji dana vrijednost povezana s
	 * nekim ključem u tablici, <code>false</code> inače.
	 * 
	 * @param value vrijednost povezana s nekim ključem koja se traži u tablici.
	 * @return <code>true</code> ako postoji dana vrijednost u tablici,
	 *         <code>false</code> inače.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> entry = this.table[i];

			while (entry != null) {
				if(entry.getValue() == null) {
					return true;
				}
				if (entry.getValue().equals(value)) {
					return true;
				}
				entry = entry.next;
			}
		}

		return false;
	}

	/**
	 * Metoda koja uklanja par iz tablice čiji je ključ dana vrijednost.
	 * 
	 * @param key ključ za par koji se treba ukloniti.
	 * @return vrijednost koja je vezana za ključ para koji je uklonjen.
	 */
	public V remove(K key) {
		if (key == null) {
			return null;
		}

		int index = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> entry = this.table[index];
		TableEntry<K, V> lastEntry = null;

		while (entry != null) {
			if (entry.getKey().equals(key)) {
				if (lastEntry == null && entry.next == null) {
					V oldValue = entry.getValue();
					this.table[index] = null;
					this.size--;
					this.modificationCount++;
					return oldValue;
				} else if (lastEntry == null) {
					V oldValue = entry.getValue();
					this.table[index] = entry.next;
					this.size--;
					this.modificationCount++;
					return oldValue;
				} else {
					lastEntry.next = entry.next;
					this.size--;
					this.modificationCount++;
					return entry.getValue();
				}
			}
			lastEntry = entry;
			entry = entry.next;
		}

		return null;
	}

	/**
	 * Metoda koja vraća <code>true</code> ako ova tablica ne sadrži niti jedan par,
	 * inače vraća <code>false</code>.
	 * 
	 * @return true ako je tablica prazna, false inače.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		String result = new String("");
		TableEntry<K, V> entry;
		boolean first = true;

		for (int i = 0; i < this.table.length; i++) {
			entry = this.table[i];

			if (entry == null)
				continue;

			while (entry != null) {
				if (first) {
					first = false;
				} else {
					result += ", ";
				}
				result += entry.toString();
				entry = entry.next;
			}
		}

		return "[" + result + "]";
	}

	/**
	 * Metoda koja vraća polje parova ključ vrijednost ove tablice. Elementi su
	 * poredani prema zapisu u tablici.
	 * 
	 * @return polje parova ove tablice.
	 */
	public TableEntry<K, V>[] toArray() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[this.size];
		TableEntry<K, V> entry;
		int index = 0;

		for (int i = 0; i < this.table.length; i++) {
			entry = this.table[i];

			while (entry != null) {
				array[index++] = entry;
				entry = entry.next;
			}
		}

		return array;
	}

	/**
	 * Metoda koja briše sve uređene parove iz tablice.
	 */
	public void clear() {
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}
		this.size = 0;
		this.modificationCount++;
	}

	/**
	 * Pomoćna metoda koja uvećava tablicu dva puta i ponovo stavlja uređene parove
	 * u tablicu.
	 */
	@SuppressWarnings("unchecked")
	private void expandTable() {
		TableEntry<K, V>[] array = this.toArray();
		K key;
		V value;

		this.table = (TableEntry<K, V>[]) new TableEntry[this.table.length * 2];
		this.modificationCount++;
		this.size = 0;

		for (int i = 0; i < array.length; i++) {
			key = array[i].getKey();
			value = array[i].getValue();
			this.put(key, value);
		}
	}

	@Override
	public Iterator<SimpleHashTable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Privatna klasa koja implementira {@link Iterator} za iteriranje kroz {@link SimpleHashTable}.
	 * 
	 * @author Cubi
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashTable.TableEntry<K, V>> {
		private int savedModificationCount;
		private int index;
		private TableEntry<K, V> currentEntry;

		public IteratorImpl() {
			this.savedModificationCount = modificationCount;
			this.index = 0;
			currentEntry = null;
		}

		/**
		 * @throws ConcurrentModificationException baca se ako je tablica mijenjana za vrijeme rada ovog iteratora.
		 */
		@Override
		public boolean hasNext() {
			if (this.savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Tablica je mijenjanja tijekom rada ovog iteratora!");
			}

			return index < size;
		}

		/**
		 * @throws ConcurrentModificationException baca se ako je tablica mijenjana za vrijeme rada ovog iteratora.
		 * @throws NoSuchElementException baca se ako nema dostupnih elemenata preko ovog iteratora.
		 */
		@Override
		public SimpleHashTable.TableEntry<K, V> next() {
			if (this.savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Tablica je mijenjanja tijekom rada ovog iteratora!");
			}
			if (index >= size) {
				throw new NoSuchElementException("Nema više dostupnih elemenata za pristupit preko ovog iteratora!");
			}

			TableEntry<K, V> entry;
			int i = 0, j = 0;
			while (j < table.length) {
				if (table[j] == null) {
					j++;
					continue;
				}

				entry = table[j];
				while (entry != null) {
					if (i == index) {
						index++;
						this.currentEntry = entry;
						return entry;
					}
					i++;
					entry = entry.next;
				}
				j++;
			}

			return null;
		}

		/**
		 * @throws ConcurrentModificationException baca se ako je tablica mijenjana za vrijeme rada ovog iteratora.
		 * @throws IllegalStateException baca se ako se ova metoda pozove dvaput zaredom bez prethodnog poziva metode next().
		 */
		@Override
		public void remove() {
			if (this.savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Tablica je mijenjanja tijekom rada ovog iteratora!");
			}

			if (this.currentEntry == null) {
				throw new IllegalStateException("Nije moguće obaviti ovu operaciju!");
			} else {
				SimpleHashTable.this.remove(this.currentEntry.getKey());
				this.savedModificationCount++;
				this.index--;
				this.currentEntry = null;
			}
		}
	}
}