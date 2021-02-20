package hr.fer.oprpp1.hw04.db;

/**
 * Klasa koja predstvalja jedan zapis o studentu. Zapis sadrži JMBAG, ime,
 * prezime i ocjenu.
 * 
 * @author Cubi
 *
 */
public class StudentRecord {
	private String jmbag;
	private String firstName;
	private String lastName;
	private int finalGrade;

	/**
	 * Konstruktor za klasu {@link StudentRecord} koji inicijalizira sve potrebne
	 * vrijednosti.
	 * 
	 * @param jmbag      jmbag studenta
	 * @param firstName  ime studenta
	 * @param lastName   prezime studenta
	 * @param finalGrade konačna ocjena studenta
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Metoda koja vraća jmbag ovog studenta.
	 * 
	 * @return jmbag ovog studenta.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Metoda koja vraća ime ovog studenta.
	 * 
	 * @return ime ovog studenta.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Metoda koja vraća prezime ovog studenta.
	 * 
	 * @return prezime ovog studenta.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metoda koja konačnu ocjenu jmbag ovog studenta.
	 * 
	 * @return konačnu ocjenu ovog studenta.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
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
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.jmbag + "\t" + this.firstName + "\t" + this.lastName + "\t" + this.finalGrade;
	}
	
	
}
