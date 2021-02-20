package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element koji čuva vrijednost jedne decimalne konstante.
 * 
 * @author Cubi
 *
 */
public class ElementConstantDouble extends Element{
	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString(this.value);
	}

}
