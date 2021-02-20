package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja naredbu koja generira neki tekstualni izlaz dinamički.
 * 
 * @author Cubi
 *
 */
public class EchoNode extends Node {
	private Element[] elements;

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	/**
	 * Metoda koja vraća sve elemente ovog čvora
	 * 
	 * @return polje elemenata
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		String str = new String("{$=");
		
		for(Element e : elements) {
			str += " " + e.asText();
		}
		
		str += " $}";
		return str;
	}
	
}
