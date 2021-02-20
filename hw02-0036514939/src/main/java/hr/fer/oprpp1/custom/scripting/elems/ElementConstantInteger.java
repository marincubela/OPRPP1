package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element koji čuva vrijednost jedne cjelobrojne konstante.
 * 
 * @author Cubi
 *
 */
public class ElementConstantInteger extends Element {
	private int value;
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
}
