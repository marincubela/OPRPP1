package hr.fer.oprpp1.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.oprpp1.java.gui.calc.model.CalculatorInputException;

public class ButtonBinaryOperator extends ButtonCalc {
	private static final long serialVersionUID = 1L;
	
	public ButtonBinaryOperator(CalcModelImpl model, DoubleBinaryOperator op, String text) {
		super(text);
		addActionListener(l -> {
			if(model.hasFrozenValue()) {
				throw new CalculatorInputException("Nije moguće obaviti operaciju!");
			}
			
			if(model.isActiveOperandSet()) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.freezeValue(Double.toString(result));
				model.setActiveOperand(result);
				model.setPendingBinaryOperation(op);
				model.clear();
			} else {
				model.freezeValue(model.toString());
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(op);
				model.clear();
			}
		});		
	}

}
