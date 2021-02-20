package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Klasa koja modelira bazu podataka zapisa o studentima.
 * 
 * @author Cubi
 *
 */
public class StudentDatabase {
	private Map<String, StudentRecord> hashedRecords;
	private List<StudentRecord> records;
	/**
	 * Konstruktor za klasu {@link StudentDatabase} koji prima polje Stringova u
	 * kojemu su zapisi o studentima. Ti se zapisi parsiraju i spremaju u mapu gdje
	 * je jmbag ključ, a vrijednost zapis o studentu.
	 * 
	 * @param lines lista zapisa o studentima
	 */
	public StudentDatabase(List<String> lines) {
		hashedRecords = new HashMap<>();
		records = new ArrayList<>();

		for (String line : lines) {
			String[] record = line.split("\t");

			String jmbag = record[0];
			if (hashedRecords.containsKey(jmbag)) {
				System.err.println("Invalid record! Jmbag " + jmbag + " already exists!");
				System.exit(1);
			}
			String lastName = record[1];
			String firstName = record[2];
			int finalGrade = Integer.parseInt(record[3]);
			if (finalGrade < 1 || finalGrade > 6) {
				System.err.println("Final grade must be a number from 1 to 5, can't be " + finalGrade + "!");
				System.exit(1);
			}

			hashedRecords.put(jmbag, new StudentRecord(jmbag, firstName, lastName, finalGrade));
			records.add(new StudentRecord(jmbag, firstName, lastName, finalGrade));
		}
	}

	/**
	 * Metoda koja vraća zapis o studentu s danim jmbagom.
	 * 
	 * @param jmbag studenta koji se traži.
	 * @return zapis o studentu s danim jmbagom.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return this.hashedRecords.get(jmbag);
	}

	/**
	 * Metoda vraća zapise o studentima koji je dani filter prihvaća.
	 * 
	 * @param filter koji se određuje prihvaća li se određeni zapis ili ne.
	 * @return listu zapisa o studentima koji zadovoljavaju dani filter.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		return this.records.stream()
				.filter(x -> filter.accepts(x))
				.collect(Collectors.toList());

	}
}
