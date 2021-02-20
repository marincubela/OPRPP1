package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.DocumentNode;
import hr.fer.oprpp1.custom.scripting.elems.EchoNode;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.elems.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.elems.Node;
import hr.fer.oprpp1.custom.scripting.elems.TextNode;

/**
 * Klasa koja predstavlja parser koji uz pomoć klase {@link Lexer} parsirani dokument.
 * 
 * @author Cubi
 *
 */
public class SmartScriptParser {
	private String docBody;
	private Lexer lexer;
	private DocumentNode docNode;
	
	/**
	 * Konstruktor za klasu {@link SmartScriptParser} koji prima tekstualni dokument koji će se parsirati.
	 * 
	 * @param docBody tekst koji se parsira.
	 */
	public SmartScriptParser(String docBody) {
		this.docBody = docBody;
		this.lexer = new Lexer(this.docBody);
		this.docNode = new DocumentNode();
		try {
			parseDocument();
			
		} catch(LexerException e) {
			throw new SmartScriptParserException(e);
		}
	}
	
	/**
	 * Vraća čvor ovog dokumenta.
	 * 
	 * @return čvor ovog dokumenta.
	 */
	public DocumentNode getDocumentNode() {
		return this.docNode;
	}

	/**
	 * Metoda koja izvodi parsiranje teksta.
	 * 
	 * @throws SmartScriptParserException baca se u slučaju netočno napisanog dokumenta (dokument se ne može parsirati).
	 */
	private void parseDocument() {
		this.lexer.setState(LexerState.TEXT);
		
		Node currentNode = new Node();
		currentNode = this.docNode;
		
		ObjectStack stack = new ObjectStack();
		stack.push(currentNode);
		
		while(this.lexer.getNextToken().getType() != TokenType.EOF) { // Dok ne dođemo do kraja datoteke
			Token token = this.lexer.getToken();
			currentNode = (Node) stack.peek();
			// Ako je idući token tekst onda lexer ne smije biti u stanju tag.
			if(token.getType() == TokenType.TEXT && this.lexer.getState() != LexerState.TAG) {
				TextNode tNode = new TextNode((String) token.getValue());
				currentNode.addChildNode(tNode);
				continue;
			}
			// Ako je idući token {$ prebaci lekser u stanje tag, a ako je već bio baci iznimku jer ne smije biti.
			if(token.getType() == TokenType.TAGBEGIN) {
				if(this.lexer.getState() == LexerState.TAG) {
					throw new SmartScriptParserException("Jedan tag se ne može nalaziti u drugom tagu!");
				}
				
				this.lexer.setState(LexerState.TAG);
				// Kolekcija za sve tokene u tagu
				ArrayIndexedCollection tagTokens = new ArrayIndexedCollection();
				// Pokupi sve tokene u tagu
				while(lexer.getNextToken().getType() != TokenType.EOF && lexer.getToken().getType() != TokenType.TAGEND) {
					token = lexer.getToken();
					
					if(token.getType() == TokenType.TAGBEGIN) {
						throw new SmartScriptParserException("Jedan tag se ne može nalaziti u drugom tagu!");
					} else if(token.getType() == TokenType.TEXT) {
						throw new SmartScriptParserException("Tekst se ne smije nalazit u tagu!");
					}
					
					tagTokens.add(token);
				}
				// Tag mora završiti $} inače je došlo do pogreške
				if(lexer.getToken().getType() != TokenType.TAGEND) {
					throw new SmartScriptParserException("Tag nije zatvoren! Nevaljali zapis taga!");
				}
				// Pročitani su svi tokeni iz taga, vrati se u stanje za tekst dokumneta
				this.lexer.setState(LexerState.TEXT);
				
				Token tagName = (Token) tagTokens.get(0);
				tagTokens.remove(0);
				// Provjeri koja je vrsta taga i za svaku odradi odgovarajuću akciju
				if(tagName.getType() == TokenType.EQUAL) {
					EchoNode emptyTag = new EchoNode(tokensToElements(tagTokens));
					currentNode.addChildNode(emptyTag);
				} else if(((String) tagName.getValue()).toUpperCase().equals(new String("FOR"))) {
					if(tagTokens.size() != 3 && tagTokens.size() != 4) {
						throw new SmartScriptParserException("FOR tag mora imati bar 3, a najviše 4 elementa u sebi, a ima " + tagTokens.size() + "!");
					}
					
					Element[] elem = tokensToElementsFOR(tagTokens);
					
					if(!(elem[0] instanceof ElementVariable)) {
						throw new SmartScriptParserException("FOR tag na prvom mjestu mora imati varijablu!");
					}
					
					ForLoopNode fNode = new ForLoopNode((ElementVariable)elem[0], elem[1], elem[2], elem[3]);					
					currentNode.addChildNode(fNode);
					stack.push(fNode);
				} else if(((String) tagName.getValue()).toUpperCase().equals("END")) {
					stack.pop();
				}
			}	
		}
	}

	/**
	 * Pretvara polje tokena u polje elemenata sukladno tipu tokenu.
	 * 
	 * @param tagTokens polje tokena
	 * @return polje elemenata
	 */
	private Element[] tokensToElements(ArrayIndexedCollection tagTokens) {
		Element[] elements = new Element[tagTokens.size()];
		int index = 0;
		
		ElementsGetter e = tagTokens.createElementsGetter();
		
		while(e.hasNextElement()) {
			Token t = (Token) e.getNextElement();
			
			if(t.getType() == TokenType.DOUBLE) {
				ElementConstantDouble elem = new ElementConstantDouble((Double) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.INTEGER) {
				ElementConstantInteger elem = new ElementConstantInteger((Integer) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.STRING) {
				ElementString elem = new ElementString((String) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.OPERATOR) {
				ElementOperator elem = new ElementOperator ((String) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.FUNCTION) {
				ElementFunction elem = new ElementFunction((String) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.VARIABLE) {
				ElementVariable elem = new ElementVariable((String) t.getValue());
				elements[index++] = elem;
			}
		}
		
		return elements;
	}
	
	/**
	 * Pretvara polje tokena u polje elemenata sukladno tipu tokenu za FOR petlju.
	 * 
	 * @param tagTokens polje tokena
	 * @return polje elemenata
	 */
	private Element[] tokensToElementsFOR(ArrayIndexedCollection tagTokens) {
		Element[] elements = new Element[tagTokens.size()];
		int index = 0;
		
		ElementsGetter e = tagTokens.createElementsGetter();
		
		while(e.hasNextElement()) {
			Token t = (Token) e.getNextElement();
			
			if(t.getType() == TokenType.DOUBLE) {
				ElementConstantDouble elem = new ElementConstantDouble((Double) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.INTEGER) {
				ElementConstantInteger elem = new ElementConstantInteger((Integer) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.STRING) {
				ElementString elem = new ElementString((String) t.getValue());
				elements[index++] = elem;
			} else if(t.getType() == TokenType.VARIABLE) {
				ElementVariable elem = new ElementVariable((String) t.getValue());
				elements[index++] = elem;
			} else {
				throw new SmartScriptParserException("U FOR petlji se ne smije nalaziti " + t.getType() + "!");
			}
		}
		
		return elements;
	}
	
}
