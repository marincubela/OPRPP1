package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje predstavlja listu objekata tipa T.
 * 
 * @author Cubi
 *
 * @param <T>
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * Metoda koja vraća element koji se nalazi na danom mjestu u kolekciji ako
	 * takav postoji. Ukoliko ne postoji, metoda baca iznimku.
	 * 
	 * @param index pozicija elementa koji se traži u kolekciji.
	 * @return element kolekcije na traženoj poziciji.
	 * @throws IndexOutOfBoundsException iznimka za dana mjesta koja ne postoje u
	 *                                   kolekciji.
	 */
	Object get(int index);
	
	/**
	 * Metoda koja dodaje dani objekt u kolekciju na danu poziciju. Sve ostale
	 * vrijednosti ostaju sačuvane, a elementi koji su se nalazi na poziciji ili
	 * poslije nje su pomaknuti za jedno mjesto. Valjane vrijednosti za poziciju su
	 * svi brojevi između 0 i veličine kolekcije. Ako metoda primi nevaljanu
	 * vrijednost za poziciju, baca se iznimka {@link IndexOutOfBoundsException}.
	 * 
	 * @param value    objekt koji dodajemo u kolekciju,
	 * @param position pozicija na kojoj će objekt biti dodan.
	 * @throws IndexOutOfBoundsException baca se za neispravne vrijednosti pozicije.
	 * @throws NullPointerException      baca se ako je predani objekt
	 *                                   <code>null</code>.
	 */
	void insert(T value, int position);
	
	/**
	 * Metoda traži predani objekt u kolekciji i vraća poziciju prvog objekta koji
	 * je jednak danom objektu. Ako kolekcija ne sadrži dani objekt, metoda vraća
	 * -1. U to se podrazumijeva i ako je predani objekt <code>null</code>.
	 * 
	 * @param value objekt čija se pozicija traži u kolekciji.
	 * @return pozicija prvog objekta koji je jednak danom objektu, ako takvog nema
	 *         onda -1.
	 */
	int indexOf(Object value);
	
	/**
	 * Metoda koja uklanja element na danoj poziciji iz kolekcije. Svi ostali
	 * elementi se pomjeraju na jednu poziciju ispred. Ako je dana pozicija
	 * neispravna, baca iznimku {@link IndexOutOfBoundsException}.
	 * 
	 * @param index pozicija elementa koji se uklanja iz kolekcije
	 * @throws IndexOutOfBoundsException baca se u slučaju neispravne pozicije
	 */
	void remove(int index);
}
