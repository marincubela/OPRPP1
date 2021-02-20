package hr.fer.oprpp1.java.gui.calc;

import java.util.function.UnaryOperator;

public class ButtonUnaryOperator extends ButtonCalc {
	private static final long serialVersionUID = 1L;
	
	private UnaryOperator<Double> op1;
	private UnaryOperator<Double> op2;
	private String text1;
	private String text2;
	private boolean isFirst;
	
	public ButtonUnaryOperator(CalcModelImpl model, UnaryOperator<Double> op1, UnaryOperator<Double> op2,
			String text1, String text2) {
		super(text1);
		this.op1 = op1;
		this.op2 = op2;
		this.text1 = text1;
		this.text2 = text2;
		isFirst = true;
		addActionListener(l -> {
			model.setValue(this.getCurrentOperator().apply(model.getValue()));
		});
	}
	
	public void inverse() {
		isFirst = !isFirst;
		this.setText(getCurrentText());
	}
	
	private String getCurrentText() {
		return isFirst ? text1 : text2;
	}
	
	private UnaryOperator<Double> getCurrentOperator() {
		return isFirst ? op1 : op2;
	}

}
