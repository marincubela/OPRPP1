package hr.fer.oprpp1.java.gui.calc;

public class ButtonDigit extends ButtonCalc {

	private static final long serialVersionUID = 1L;

	public ButtonDigit(CalcModelImpl model, int digit) {
		super(Integer.toString(digit));
		setFont(this.getFont().deriveFont(30f));
		addActionListener(l -> {
			model.insertDigit(digit);
		});
	}
}
