package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja čvor za jednu for petlju.
 * 
 * @author Cubi
 *
 */
public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	/**
	 * Konstruktor za klasu {@link ForLoopNode} kojim se inicijaliziraju vrijednosti.
	 * 
	 * @param variable 
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Metoda koja vraća varijablu ovog čvora koja se koristi u for petlji.
	 * 
	 * @return varijablu koja se koristi u for petlji.
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Metoda koja vraća početni izraz ovog čvora.
	 * 
	 * @return početni izraz ovog čvora.
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Metoda koja vraća krajnji izraz ovog čvora.
	 * 
	 * @return krajnji izraz ovog čvora.
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Metoda koja vraća korak for petlje ovog čvora.
	 * 
	 * @return korak for petlje ovog čvora.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		String str = new String("{$ FOR ");
		str += this.getVariable().asText() + " " + this.getStartExpression().asText()
				+ " " + this.getEndExpression().asText();
		if(this.getStartExpression() != null) {
			str +=  " " + this.getStartExpression().asText();
		}
		str += " $}";
		
		for(int i = 0; i < this.numberOfChildren(); i++) {
			str += this.getChild(i).toString();
		}
		
		str += "{$ END $}";
		
		return str;
	}
}
