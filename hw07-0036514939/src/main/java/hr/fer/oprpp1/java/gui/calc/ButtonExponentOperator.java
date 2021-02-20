package hr.fer.oprpp1.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.oprpp1.java.gui.calc.model.CalculatorInputException;

public class ButtonExponentOperator extends ButtonCalc {
	private static final long serialVersionUID = 1L;

	private DoubleBinaryOperator op1;
	private DoubleBinaryOperator op2;
	private String text1;
	private String text2;
	private boolean isFirst;

	public ButtonExponentOperator(CalcModelImpl model, DoubleBinaryOperator op1, DoubleBinaryOperator op2, String text1,
			String text2) {
		super(text1);
		this.op1 = op1;
		this.op2 = op2;
		this.text1 = text1;
		this.text2 = text2;
		isFirst = true;
		addActionListener(l -> {
			if (model.hasFrozenValue()) {
				throw new CalculatorInputException("Nije moguće obaviti operaciju!");
			}

			if (model.isActiveOperandSet()) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());
				model.freezeValue(Double.toString(result));
				model.setActiveOperand(result);
				model.setPendingBinaryOperation(getCurrentOperator());
				model.clear();
			} else {
				model.freezeValue(model.toString());
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(getCurrentOperator());
				model.clear();
			}
		});
	}

	public void inverse() {
		isFirst = !isFirst;
		this.setText(getCurrentText());
	}

	private String getCurrentText() {
		return isFirst ? text1 : text2;
	}

	private DoubleBinaryOperator getCurrentOperator() {
		return isFirst ? op1 : op2;
	}
}
