package hr.fer.oprpp1.custom.collections;

public class Dictionary<K, V> {
	private ArrayIndexedCollection<Pair<K, V>> dictionary;

	/**
	 * Privatni statički razred koji modelira par ključa i vrijednosti pohranjene za
	 * taj ključ. Ključ ne smije biti <code>null</code> dok vrijednost vezana za taj
	 * ključ može.
	 * 
	 * @author Cubi
	 *
	 * @param <K> tip ključa.
	 * @param <V> tip vrijednosti.
	 */
	private static class Pair<K, V> {
		private K key;
		private V value;

		/**
		 * Konstruktor za privatnu statičku klasu {@link Pair} koja inicijalizira ključ
		 * i vrijedost.
		 * 
		 * @param key   vrijednost za ključ ovog para.
		 * @param value vrijednost za vrijednost ovog para.
		 */
		public Pair(K key, V value) {
			this.setKey(key);
			this.setValue(value);
		}

		/**
		 * Metoda koja vraća ključ ovog para.
		 * 
		 * @return ključ ovog para.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Metoda koja postavlja ključ na danu vrijednost parametra. Ključ ne smije biti
		 * <code>null</code>.
		 * 
		 * @param key vrijednost na koju se ključ postavi.
		 * @throws NullPointerException baca se ako je dana vrijednost
		 *                              <code>null</code>.
		 */
		public void setKey(K key) {
			if (key == null) {
				throw new NullPointerException("Vrijednost ključa ne smije biti null!");
			}
			this.key = key;
		}

		/**
		 * Metoda koja vraća vrijednost ovog para.
		 * 
		 * @return vrijednost ovog para.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Metoda koja postavlja vrijednost na danu vrijednost parametra.
		 * 
		 * @param key vrijednost na koju se vrijednost para postavi.
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}

	/**
	 * Pretpostavljeni konstruktor za klasu {@link Dictionary} koji inicijalizira
	 * potrebne vrijednosti.
	 */
	public Dictionary() {
		this.dictionary = new ArrayIndexedCollection<>();
	}

	/**
	 * Metoda koja vraća <code>true</code> ako ovaj rječnik ne sadrži niti jedan
	 * objekt, inače vraća <code>false</code>.
	 * 
	 * @return true ako je rječnik prazan, false inače.
	 */
	public boolean isEmpty() {
		return this.dictionary.isEmpty();
	}

	/**
	 * Metoda koja vraća broj pohranjenih parova ključ, vrijednost za ovaj rječnik.
	 * 
	 * @return broj parova u rječniku.
	 */
	public int size() {
		return this.dictionary.size();
	}

	/**
	 * Metoda koja uklanja sve parove iz rječnika.
	 */
	public void clear() {
		this.dictionary.clear();
	}

	/**
	 * Metoda koja dodaje novi par u rječnik ako već ne postoji isti ključ i tada
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

		for (int i = 0; i < this.size(); i++) {
			if (this.dictionary.get(i).getKey().equals(key)) {
				V oldValue = this.dictionary.get(i).getValue();
				this.dictionary.get(i).setValue(value);
				return oldValue;
			}
		}

		this.dictionary.add(new Pair<K, V>(key, value));
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

		for (int i = 0; i < this.size(); i++) {
			if (this.dictionary.get(i).getKey().equals(key)) {
				return this.dictionary.get(i).getValue();
			}
		}

		return null;
	}

	/**
	 * Metoda koja uklanja par iz rječnika čiji je ključ dana vrijednost.
	 * 
	 * @param key ključ za par koji se treba ukloniti.
	 * @return vrijednost koja je vezana za ključ para koji je uklonjen.
	 */
	public V remove(K key) {
		if (key == null) {
			return null;
		}

		V value;
		for (int i = 0; i < this.size(); i++) {
			if (this.dictionary.get(i).getKey().equals(key)) {
				value = this.dictionary.get(i).getValue();
				this.dictionary.remove(i);
				return value;
			}
		}

		return null;
	}
}