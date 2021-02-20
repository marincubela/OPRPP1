package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element koji čuva ime jedne funkcije.
 * 
 * @author Cubi
 *
 */
public class ElementFunction extends Element{
private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
}
