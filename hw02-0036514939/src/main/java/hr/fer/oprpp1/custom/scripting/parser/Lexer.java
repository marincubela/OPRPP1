package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Leksički analizator za {@link SmartScriptParser}.
 * 
 * @author Cubi
 *
 */
public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;

	/**
	 * Konstruktor za {@link Lexer} koji prima cijeli tekst koji se treba
	 * analizirati. Inicijaliziraju se i potrebne vrijednosti za početak analize.
	 * 
	 * @param docBody tekst koji se analizira.
	 */
	public Lexer(String docBody) {
		this.data = docBody.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.TEXT;
	}

	/**
	 * Metoda koja traći sljedeći token u danom tekstu i vraća ga ako ga uspješno
	 * nađe.
	 * 
	 * @return sljedeći token.
	 * @throws LexerException baca se u slučaju pogreške kod analize teksta.
	 */
	public Token getNextToken() {
		if (this.token != null && this.token.getType() == TokenType.EOF) {
			throw new LexerException("Nema dostupnih tokena!");
		}
		// Ako se dođe do kraja, postavi zadnji token na kraj datoteke
		if (this.currentIndex >= this.data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.getToken();
		}
		// U stanju lexera TEXT, izvuci tekstualni dio.
		if (this.getState() == LexerState.TEXT) {
			return extractText();
		} else if (this.getState() == LexerState.TAG) { // U stanju TAG grupiraj sve tokene unutar taga
			skipBlanks();

			// Provjera koji token je sljedeći
			char c = data[currentIndex++];
			if (c == '=') {
				this.token = new Token(TokenType.EQUAL, c);
				return this.getToken();
			} else if (Character.isLetter(c)) {
				return extractVariable(c);
			} else if (Character.isDigit(c)
					|| c == '-' && currentIndex < data.length && Character.isDigit(data[currentIndex + 1])) {
				return extractNumber(c);
			} else if (c == '@') {
				return extractFunction(c);
			} else if (isOperator(c)) {
				this.token = new Token(TokenType.OPERATOR, Character.toString(c));
				return this.getToken();
			} else if (c == '\"') {
				return extractString();
			} else { // Kraj taga
				if (c == '$' && currentIndex < data.length && data[currentIndex] == '}') {
					this.token = new Token(TokenType.TAGEND, new String("$}"));
					this.currentIndex++;
					return this.getToken();
				} else { // Sljedećim znak ne može započeti nijednu leksičku jedinku.
					throw new LexerException("Dokument se ne može parsirati! Znak " + c
							+ " ne može biti početak nijedne leksičke jedinke!");
				}
			}
		} else {	// Ako stanje nije ni TAG ni TEXT
			throw new LexerException("Lexer se našao u nepostojoćem stanju!");
		}
	}

	/**
	 * Pomoćna metoda koja iz danog teksta izvlači string.
	 * 
	 * @param c trenutni znak
	 * @return token koji predstavlja string.
	 * @throws LexerException baca se u slučaju nevaljane escape sekvence.
	 */
	private Token extractString() {
		char c;
		String s = new String("");

		while (currentIndex < data.length) {
			c = this.data[this.currentIndex++];
			if (c == '\\') {	// Escape sekvence u stringu
				c = this.data[this.currentIndex++];
				switch (c) {
				case 'n':
					s += '\n';
					break;
				case 'r':
					s += '\r';
					break;
				case 't':
					s += '\t';
					break;
				case '\\':
					s += '\\';
					break;
				case '\"':
					s += '\"';
					break;
				default:
					throw new LexerException("Nevaljana escape sekvenca u Stringu \\" + c + "!");
				}
			} else if (c == '\"') {
				break;
			} else {
				s += c;
			}
		}

		this.token = new Token(TokenType.STRING, s);
		return this.getToken();
	}

	/**
	 * Pomoćna metoda koja iz danog teksta izvlači ime funkcije.
	 * 
	 * @param c trenutni znak
	 * @return token koji predstavlja ime varijable.
	 * @throws LexerException baca se ako ime funkcije nije valjano.
	 */
	private Token extractFunction(char c) {
		String name = new String("");
		name += c;
		if (currentIndex < data.length) {
			c = this.data[this.currentIndex++];
			if (Character.isLetter(c)) {
				name += c;
			} else {
				throw new LexerException("Nevaljano ime funkcije! Ime funkcije ne može započeti s " + c + "!");
			}
		}

		c = this.data[this.currentIndex];

		while (currentIndex < data.length && (Character.isLetter(c) || Character.isDigit(c) || c == '_')) {
			this.currentIndex++;
			name += c;
			c = this.data[this.currentIndex];
		}

		this.token = new Token(TokenType.FUNCTION, name);
		return this.getToken();
	}

	/**
	 * Pomoćna metoda koja iz danog teksta izvlači brojeve. Ako se broj može
	 * prikazati koa cijelobrojna konstanta onda se vraća token koji to i
	 * predstavlja. Analogno za double.
	 * 
	 * @param c trenutni znak
	 * @return token koji predstavlja odgovarajući broj.
	 */
	private Token extractNumber(char c) {
		String number = new String("");
		number += c;

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			c = this.data[this.currentIndex++];
			number += c;
		}

		if (currentIndex < data.length && data[currentIndex] == '.') {
			number += this.data[this.currentIndex++];
		} else {
			this.token = new Token(TokenType.INTEGER, Integer.parseInt(number));
			return this.getToken();
		}

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			c = this.data[this.currentIndex++];
			number += c;
		}

		this.token = new Token(TokenType.DOUBLE, Double.parseDouble(number));
		return this.getToken();
	}

	/**
	 * Pomoćna metoda koja iz danog teksta izvlači ime varijable.
	 * 
	 * @param c trenutni znak
	 * @return token koji predstavlja ime varijable.
	 */
	private Token extractVariable(char c) {
		String name = new String("");
		name += c;
		c = this.data[this.currentIndex];

		while (currentIndex < data.length && (Character.isLetter(c) || Character.isDigit(c) || c == '_')) {
			this.currentIndex++;
			name += c;
			c = this.data[this.currentIndex];
		}

		this.token = new Token(TokenType.VARIABLE, name);
		return this.getToken();
	}

	/**
	 * Pomoćna metoda koja iz danog teksta izvlači tekst dokumenta.
	 * 
	 * @return token koji predstavlja tekst.
	 * @throws LexerException baca se u slučaju nevaljane escape sekvence.
	 */
	private Token extractText() {
		String text = new String("");

		while (this.currentIndex < this.data.length) {
			char c = this.data[this.currentIndex++];
			if (c == '{' && currentIndex < data.length && data[currentIndex] == '$') {
				if (text.equals("")) {
					this.token = new Token(TokenType.TAGBEGIN, "{$");
					this.currentIndex++;
					return this.getToken();
				} else {
					this.token = new Token(TokenType.TEXT, text);
					this.currentIndex--;
					return this.getToken();
				}
			}

			if (c == '\\') {
				if (this.currentIndex < data.length && data[currentIndex] == '{' || data[currentIndex] == '\\') {
					c = data[currentIndex++];
				} else {
					throw new LexerException("Nevaljana escape sekvenca van TAG-a! Dopuštene su samo \\\\ i \\{, a bila je \\"
							+ data[currentIndex]);
				}
			}

			text += c;
		}

		this.token = new Token(TokenType.TEXT, text);
		return this.getToken();
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
	 * Metoda koja provjerava je li predani znak operator.
	 * 
	 * @param c znak koji se provjerava
	 * @return <code>true</code> ako je znak operator, <code>false</code> inače.
	 */
	private boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metoda koja vraća trenutno stanje leksera.
	 * 
	 * @return trenutno stanje leksera.
	 */
	public LexerState getState() {
		return state;
	}

	/**
	 * Metoda koja postavlja trenutno stanje lexera u predano stanje.
	 * 
	 * @param state novo stanje leksera.
	 */
	public void setState(LexerState state) {
		this.state = state;
	}

	/**
	 * Metoda koja vraća posljednji generirani token
	 * 
	 * @return posljednji generirani token
	 */
	public Token getToken() {
		return this.token;
	}
}
