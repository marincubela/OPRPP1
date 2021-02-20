package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element koji čuva vrijednost jednog stringa.
 * 
 * @author Cubi
 *
 */
public class ElementString extends Element{
	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return "\"" + this.value + "\"";
	}
}
