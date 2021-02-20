package hr.fer.oprpp1.custom.collections;

/**
 * Klasa koja predstavlja pohranu podataka na stogu uz pomoć {@link ArrayIndexedCollection}.
 * Sadrži sve uobičajene metode za stog poput push, pop, peek, isEmpty itd.
 * 
 * @author Cubi
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection stack;
	
	/**
	 * Pretpostavljeni konstruktor za ovu klasu koji inicijalizira kolekciju za pohranu podataka.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Metoda koja vraća <code>true</code> ako ovaj stog ne sadrži niti jedan objekt, inače vraća <code>false</code>.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, <code>false</code> inače
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Metoda koja vraća broj objekata pohranjenih u ovom stogu.
	 * @return broj objekata na stogu
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Metoda koja dodaje jedan objekt na vrh stoga.
	 * Stog ne prima <code>null</code> vrijednosti i bacit će se {@link NullPointerException} ukoliko se pokuša dodati.
	 * 
	 * @param value objekt koji se dodaje na stog.
	 * @throws NullPointerException baca se ako se pokuša dodati <code>null</code> na stog.
	 */
	public void push(Object value) {
		if (value == null) {
			throw new NullPointerException("Stog ne prima null vrijednosti za elemente!");
		}
		
		stack.add(value);
	}
	
	/**
	 * Metoda koja vraća i skida objekt s vrha stoga.
	 * Ukoliko je stog prazan, baca se iznimka {@link EmptyStackException}.
	 * @return objekt skinut s vrha stoga.
	 * @throws EmptyStackException baca se ako je stog prazan.
	 */
	public Object pop() {
		if(stack.isEmpty()) {
			// TO DO exception
			throw new EmptyStackException("Stog je prazan, nema elemenata za pristupiti!");
		}
		
		Object result = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		
		return result;
	}
	
	/**
	 * Metoda koja vraća objekt koji se nalazi na vrhu stoga, ali ga ne skida sa stoga.
	 * Ukoliko je stog prazan, baca se iznimka {@link EmptyStackException}.
	 * @return objekt skinut s vrha stoga.
	 * @throws EmptyStackException baca se ako je stog prazan.
	 */
	public Object peek() {
		if(stack.isEmpty()) {
			// TO DO exception
			throw new EmptyStackException("Stog je prazan, nema elemenata za pristupiti!");
		}
		
		return stack.get(stack.size() - 1);		
	}
	
	/**
	 * Metoda koja uklanja sve objekte sa stoga.
	 */
	public void clear() {
		stack.clear();
	}
}
