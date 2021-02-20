package hr.fer.oprpp1.hw04.db;

/**
 * Klasa koja sadrži statičke implementacije dohvaćanja pojedinog polja iz zapisa o studentu.
 * 
 * @author Cubi
 *
 */
public class FieldValueGetters {
	public static final IfieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	public static final IfieldValueGetter LAST_NAME = record -> record.getLastName();
	
	public static final IfieldValueGetter JMBAG = (record) -> record.getJmbag();
}
