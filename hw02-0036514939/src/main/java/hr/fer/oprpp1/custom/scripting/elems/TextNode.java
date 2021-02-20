package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja čvor koji sadrži tekstualne podatke.
 * 
 * @author Cubi
 *
 */
public class TextNode extends Node {
	private String text;

	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Vraća tekst koji ovaj čvor čuva.
	 * 
	 * @return tekst koji je spremljen u ovom čvoru.
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		String str = new String("");
		char[] textChar= text.toCharArray();
		
		for(char c : textChar) {
			if(c != '\\' && c != '{') {
				str += c;
			} else {
				str += '\\' + c;
			}
		}
		
		return str;
	}
}
