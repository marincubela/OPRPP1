package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa koja predstavlja leksički analizator, a ujedno i parsira dani tekst.
 * 
 * @author Cubi
 *
 */
public class Lexer {

	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state; // stanje leksera

	/**
	 * Konstruktor prima ulazni tekst koji se tokenizira.
	 * 
	 * @param text ulazni tekst
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.setState(LexerState.BASIC);
		// this.nextToken();
	}
	
	/**
	 * generira i vraća sljedeći token baca LexerException ako dođe do pogreške
	 */
	public Token nextToken() {
		
		if (this.token != null && this.token.getType() == TokenType.EOF) {
			throw new LexerException("Nema dostupnih tokena!");
		}

		skipBlanks();

		if (this.currentIndex >= this.data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.getToken();
		}
		
		if(this.data[this.currentIndex] == '#' && this.state == LexerState.EXTENDED) {
			this.setState(LexerState.BASIC);
			this.currentIndex++;
			this.token = new Token(TokenType.SYMBOL, '#');
			return this.getToken();
		}
		
		if (this.currentIndex >= this.data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.getToken();
		}
		
		if(this.state == LexerState.BASIC) {
			nextBasic();
		} else if(this.state == LexerState.EXTENDED) {
			nextExtended();
		}

		return getToken();
	}

	/**
	 * Metoda za dobivanje sljedećeg tokena ako se lekser nalazi u stanju EXTENDED.
	 */
	private void nextExtended() {
		char c = this.data[this.currentIndex++];
		String word = new String("");
		while(c != '#' && c != '\n' && c != '\r' && c != ' ' && c != '\t' && this.currentIndex < this.data.length) {
			word += c;
			if(this.data[this.currentIndex] == '#') {
				break;
			}
			c = this.data[this.currentIndex++];
		}
		this.token = new Token(TokenType.WORD, word);
	}

	/**
	 * Metoda za dobivanje sljedećeg tokena ako se lekser nalazi u stanju BASIC.
	 */
	private void nextBasic() {

		if (isNumber()) {
			long number = extractNumber();
			this.token = new Token(TokenType.NUMBER, number);
		} else if (isWord()) {
			String word = extractWord();
			this.token = new Token(TokenType.WORD, word);
		} else {
			if(this.data[this.currentIndex] == '#') {
				this.setState(LexerState.EXTENDED);				
			}
			char c = this.data[this.currentIndex++];
			this.token = new Token(TokenType.SYMBOL, c);
		}
	}
	
	/**
	 * Metoda koja postavlja stanje leksera.
	 * 
	 * @param state sljedeće stanje leksera.
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Stanje leksera ne smije biti null!");
		}
		this.state = state;
	}

	/**
	 * Metoda koja iz danog teksta izvlači sljedeću riječ u danom tekstu.
	 * 
	 * @return sljedeća riječ u tekstu.
	 */
	private String extractWord() {
		String word = new String("");
		while (this.currentIndex < this.data.length && isWord()) {
			char c = this.data[this.currentIndex++];
			if (!Character.isLetter(c)) {
				c = this.data[this.currentIndex++];
			}
			word += c;
		}

		return word;
	}

	/**
	 * Metoda koja vraća može li idući znak u tekstu biti dio riječi. Ako je idući
	 * znak neko slovo, znamenka ili znak \ escapeani s znakom \ metoda vraća
	 * <code>true</code>, inače <code>false</code>.
	 * 
	 * @return <code>true</code> ako je idući znak slovo, <code>false</code> inače.
	 */
	private boolean isWord() {
		if (this.data[this.currentIndex] != '\\') {
			return Character.isLetter(this.data[this.currentIndex]);
		} else {
			if (this.currentIndex + 1 < this.data.length && (Character.isDigit(this.data[this.currentIndex + 1])
					|| this.data[this.currentIndex + 1] == '\\')) {
				return true;
			} else {
				throw new LexerException("Znak " + this.data[this.currentIndex] + " na poziciji " + this.currentIndex
						+ " nije moguće parsirati! Escape sekvenca nije valjana!");
			}
		}
	}

	/**
	 * Metoda koja iz danog teksta izvlači broj.
	 * 
	 * @return broj izvučen iz teksta.
	 */
	private long extractNumber() {
		String number = new String("");
		long l;
		while (this.currentIndex < this.data.length && isNumber()) {
			number += this.data[this.currentIndex++];
		}
		try {
			l = Long.parseLong(number);
		} catch (NumberFormatException e) {
			throw new LexerException("Broj nije moguće zapisati!" + e.getMessage());
		}
		return l;
	}

	/**
	 * Metoda koja provjerava je li idući znak u tekstu znamenka.
	 * 
	 * @return <code>true</code> ako je znak znamenka, <code>false</code> inače.
	 */
	private boolean isNumber() {
		return Character.isDigit(this.data[this.currentIndex]);
	}

	/**
	 * Metoda koja preskače praznine u danom tekstu
	 */
	private void skipBlanks() {
		while (this.currentIndex < data.length) {
			if (data[this.currentIndex] == '\n' || data[this.currentIndex] == '\t' || data[this.currentIndex] == ' '
					|| data[this.currentIndex] == '\r') {
				this.currentIndex++;
			} else {
				break;
			}
		}
	}

	/**
	 * vraća zadnji generirani token; može se pozivati više puta; ne pokreće
	 * generiranje sljedećeg tokena
	 */
	public Token getToken() {
		return this.token;
	}

}
