package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element koji čuva jedan operator.
 * 
 * @author Cubi
 *
 */
public class ElementOperator extends Element {
	private String symbol;

	public ElementOperator (String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return this.symbol;
	}
}
