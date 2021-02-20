package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.parser.Token;
import hr.fer.oprpp1.hw04.db.parser.TokenType;

/**
 * Klasa koja predsavlja parser koji parsira upit korisnika.
 * 
 * @author Cubi
 *
 */
public class QueryParser {
	@SuppressWarnings("unused")
	private String query;
	private QueryLexer lexer;
	private List<ConditionalExpression> queries;

	/**
	 * Konstruktor za klasu {@link QueryParser} koji prima upit i sprema ga.
	 * 
	 * @param query upit korisnika za bazu.
	 */
	public QueryParser(String query) {
		this.query = query;
		this.queries = new ArrayList<>();
		this.lexer = new QueryLexer(query);
		try {
			parse();
		} catch (IllegalArgumentException e) {
			throw new QueryParserException(e.getMessage());
		} catch (Exception e) {
			throw new QueryParserException("Something went wrong! Can't parse the query!");
		}
	}

	private void parse() {
		boolean first = true;

		while (lexer.getNextToken().getType() != TokenType.END) {
			Token token = lexer.getToken();
			IfieldValueGetter valueGetter = null;
			IComparisonOperator oper = null;
			String literal = new String("");

			if (first) {
				first = false;
			} else {
				if (token.getType() != TokenType.AND) {
					throw new IllegalArgumentException("Query is not parseable. Check it!");
				} else {
					token = lexer.getNextToken();
				}
			}

			if (token.getType() == TokenType.ATTRIBUTE) {
				valueGetter = (IfieldValueGetter) token.getValue();
			} else {
				throw new IllegalArgumentException("Query is not parseable. Check it!");
			}

			token = lexer.getNextToken();

			if (token.getType() == TokenType.OPERATOR) {
				oper = (IComparisonOperator) token.getValue();
			} else {
				throw new IllegalArgumentException("Query is not parseable. Check it!");
			}

			token = lexer.getNextToken();

			if (token.getType() == TokenType.STRING) {
				literal = (String) token.getValue();
			} else {
				throw new IllegalArgumentException("Query is not parseable. Check it!");
			}

			this.queries.add(new ConditionalExpression(valueGetter, literal, oper));
		}
	}

	/**
	 * Metoda koja vraća <code>true</code> ako je upit direktan, <code>false</code>
	 * inače.
	 * 
	 * @return <code>true</code> ako je upit direktan, <code>false</code> inače.
	 */
	public boolean isDirectQuery() {
		if (this.queries.size() != 1)
			return false;
		if (this.queries.get(0).getComparisonOperator() != ComparisonOperators.EQUALS)
			return false;
		if (this.queries.get(0).getFieldGetter() != FieldValueGetters.JMBAG)
			return false;

		return true;
	}

	/**
	 * Metoda vraća jmbag koji se koristio u direktnom upitu. Ako upit nije bio
	 * direktan, baca se {@link IllegalStateException}.
	 * 
	 * @return jmbag iz direktnog upita.
	 * @throws IllegalStateException ako upit nije bio direktan.
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return this.queries.get(0).getStringLiteral();
		} else {
			throw new IllegalStateException("Upit nije bio direktan!");
		}
	}

	/**
	 * Metoda vraća listu svih usporedbi koji su se našli u upitu. Ako se radi o
	 * direktnom upitu, vraća se lista s jednim elementom.
	 * 
	 * @return listu usporedbi iz upita.
	 */
	public List<ConditionalExpression> getQuery() {
		return this.queries;
	}

	private class QueryLexer {
		private char[] data;
		private Token token;
		private int currentIndex;

		public QueryLexer(String query) {
			this.data = query.toCharArray();
			currentIndex = 0;
		}

		/**
		 * Metoda koja vraća sljedeći token iz niza.
		 * 
		 * @return sljedeći token iz niza.
		 */
		public Token getNextToken() {
			if (this.token != null && this.token.getType() == TokenType.END) {
				throw new IllegalStateException("Nema dostupnih tokena!");
			}

			skipBlanks();

			// Ako se dođe do kraja, postavi zadnji token na kraj datoteke
			if (this.currentIndex >= this.data.length) {
				this.token = new Token(TokenType.END, null);
				return this.getToken();
			}

			if (isAttribute()) {
				try {
					this.token = makeAttribute();
				} catch (Exception e) {
					throw new IllegalArgumentException("Field name for student record is not valid!");
				}
			} else if (isOperator()) {
				try {
					this.token = makeOperator();
				} catch (Exception e) {
					throw new IllegalArgumentException("Operator is not valid!");
				}
			} else if (isString()) {
				try {
					this.token = makeString();
				} catch (Exception e) {
					throw new IllegalArgumentException("String literal is not valid!");
				}
			} else if (isAnd()) {
				currentIndex += 3;
				this.token = new Token(TokenType.AND, new String("AND"));
			} else {
				throw new IllegalArgumentException(
						"Query is not parseable. Can not parse token at index " + currentIndex + ".");
			}

			return this.token;
		}

		/**
		 * Metoda koja pravi token tipa string literal.
		 * 
		 * @return token tipa string literal.
		 */
		private Token makeString() {
			String str = new String("");

			while (data[++currentIndex] != '"') {
				str += data[currentIndex];
			}

			currentIndex++;
			return new Token(TokenType.STRING, str);
		}

		/**
		 * Metoda koja pravi token tipa operator.
		 * 
		 * @return token tipa operator.
		 */
		private Token makeOperator() {
			char c = data[currentIndex];

			if (c == '<') {
				if (data[currentIndex + 1] == '=') {
					currentIndex += 2;
					return new Token(TokenType.OPERATOR, ComparisonOperators.LESS_OR_EQUALS);
				} else {
					currentIndex++;
					return new Token(TokenType.OPERATOR, ComparisonOperators.LESS);
				}
			} else if (c == '>') {
				if (data[currentIndex + 1] == '=') {
					currentIndex += 2;
					return new Token(TokenType.OPERATOR, ComparisonOperators.GREATER_OR_EQUALS);
				} else {
					currentIndex++;
					return new Token(TokenType.OPERATOR, ComparisonOperators.GREATER);
				}
			} else if (c == '=') {
				currentIndex++;
				return new Token(TokenType.OPERATOR, ComparisonOperators.EQUALS);
			} else if (c == '!') {
				currentIndex += 2;
				return new Token(TokenType.OPERATOR, ComparisonOperators.NOT_EQUALS);
			} else if (c == 'L') {
				currentIndex += 4;
				return new Token(TokenType.OPERATOR, ComparisonOperators.LIKE);

			}

			return null;
		}

		/**
		 * Metoda koja pravi token tipa atribut
		 * 
		 * @return token tipa atribut.
		 */
		private Token makeAttribute() {
			if (data[currentIndex] == 'f') {
				currentIndex += 9;
				return new Token(TokenType.ATTRIBUTE, FieldValueGetters.FIRST_NAME);
			} else if (data[currentIndex] == 'l') {
				currentIndex += 8;
				return new Token(TokenType.ATTRIBUTE, FieldValueGetters.LAST_NAME);
			} else if (data[currentIndex] == 'j') {
				currentIndex += 5;
				return new Token(TokenType.ATTRIBUTE, FieldValueGetters.JMBAG);
			}
			return null;
		}

		/**
		 * Metoda koja provjerava je li sljedeći token operator and.
		 * 
		 * @return <code>true</code> ako je sljedeći token operator and,
		 *         <code>false</code> inače.
		 */
		private boolean isAnd() {
			boolean isTrue = false;
			char c = Character.toLowerCase(data[currentIndex]);
			if (c == 'a' && currentIndex + 2 < data.length) {
				String and = "and";
				isTrue = true;
				for (int i = 0; i < and.length(); i++) {
					if (and.charAt(i) != Character.toLowerCase(data[currentIndex + i])) {
						isTrue = false;
						break;
					}
				}
			}

			return isTrue;
		}

		/**
		 * Metoda koja provjerava je li sljedeći token string literal.
		 * 
		 * @return <code>true</code> ako je sljedeći token string literal,
		 *         <code>false</code> inače.
		 */
		private boolean isString() {
			return data[currentIndex] == '"';
		}

		/**
		 * Metoda koja provjerava je li sljedeći token atribut.
		 * 
		 * @return <code>true</code> ako je sljedeći token atribut, <code>false</code>
		 *         inače.
		 */
		private boolean isAttribute() {
			boolean isTrue = false;
			char c = data[currentIndex];
			if (c == 'l' && currentIndex + 7 < data.length) {
				String lastName = "lastName";
				isTrue = true;
				for (int i = 0; i < lastName.length(); i++) {
					if (lastName.charAt(i) != data[currentIndex + i]) {
						isTrue = false;
						break;
					}
				}
			} else if (c == 'f' && currentIndex + 8 < data.length) {
				String firstName = "firstName";
				isTrue = true;
				for (int i = 0; i < firstName.length(); i++) {
					if (firstName.charAt(i) != data[currentIndex + i]) {
						isTrue = false;
						break;
					}
				}
			} else if (c == 'j' && currentIndex + 4 < data.length) {
				String jmbag = "jmbag";
				isTrue = true;
				for (int i = 0; i < jmbag.length(); i++) {
					if (jmbag.charAt(i) != data[currentIndex + i]) {
						isTrue = false;
						break;
					}
				}
			}

			return isTrue;
		}

		/**
		 * Metoda koja vraća trenutni token.
		 * 
		 * @return treutni token.
		 */
		public Token getToken() {
			return this.token;
		}

		/**
		 * Metoda koja provjerava je li sljedeći token operator.
		 * 
		 * @return <code>true</code> ako je sljedeći token operator, <code>false</code>
		 *         inače.
		 */
		private boolean isOperator() {
			char c = data[currentIndex];
			if (c == '<' || c == '>') {
				if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=' || data[currentIndex + 1] == '"'
						|| isWhiteSpace(data[currentIndex + 1])) {
					return true;
				}
			} else if (c == '!' && currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
				return true;
			} else if (c == '=') {
				return true;
			} else if (c == 'L') {
				if (currentIndex + 3 < data.length && data[currentIndex + 1] == 'I' && data[currentIndex + 2] == 'K'
						|| data[currentIndex + 3] == 'E') {
					return true;
				}
			}

			return false;
		}

		/**
		 * Metoda koja vraća je li dani znak bijelina.
		 * 
		 * @param c znak
		 * @return <code>true</code> ako je dani znak bijelina, <code>false</code>
		 *         inače.
		 */
		public boolean isWhiteSpace(char c) {
			return c == ' ' || c == '\t' || c == '\n' || c == '\r';
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
	}
}
