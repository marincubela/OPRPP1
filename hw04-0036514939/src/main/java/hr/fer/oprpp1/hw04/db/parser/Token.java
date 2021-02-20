package hr.fer.oprpp1.hw04.db.parser;

/**
 * Klasa koja predstavlja jedan token u leksickoj analizi
 * @author Cubi
 *
 */
public class Token {
	private TokenType tokenType;
	private Object value;

	/**
	 * Konstruktor koji prima tip tokena i njegovu vrijednost
	 * 
	 * @param type tip tokena.
	 * @param value vrijednost tokena.
	 */
	public Token(TokenType type, Object value) {
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 * Metoda koja vraća vrijednost ovog tokena.
	 * 
	 * @return vrijednost ovog tokena.
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Metoda koja vraća tip ovog tokena.
	 * 
	 * @return tip tokena
	 */
	public TokenType getType() {
		return this.tokenType;
	}

	@Override
	public String toString() {
		return "(" + tokenType + ", " + value + ")";
	}
	
}
