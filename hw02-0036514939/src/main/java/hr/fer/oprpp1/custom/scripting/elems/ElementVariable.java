package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element koji čuva ime jedne varijable.
 * 
 * @author Cubi
 *
 */
public class ElementVariable extends Element {
	String name;
	
	public ElementVariable(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
}
