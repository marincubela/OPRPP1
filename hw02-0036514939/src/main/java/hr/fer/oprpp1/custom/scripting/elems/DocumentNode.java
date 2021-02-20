package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja čvor čitavog dokumenta.
 * 
 * @author Cubi
 *
 */
public class DocumentNode extends Node {
	
	public DocumentNode() {
		super();
	}
	
	@Override
	public String toString() {
		String str = new String("");
		
		for(int i = 0; i < this.numberOfChildren(); i++) {
			str += this.getChild(i).toString();			
		}
		
		return str;
	}
}
