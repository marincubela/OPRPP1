package hr.fer.oprpp1.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.java.gui.calc.model.CalculatorInputException;
import hr.fer.oprpp1.java.gui.layouts.CalcLayout;
import hr.fer.oprpp1.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;

	private CalcModelImpl calc;
	private Stack<Double> stack;

	public Calculator() {
		setLocation(20, 50);
		setTitle("Java Calculator v1.0.");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		stack = new Stack<>();
		calc = new CalcModelImpl();
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));

		JLabel display = new JLabel();
		display.setOpaque(true);
		display.setBackground(Color.yellow);
		display.setFont(display.getFont().deriveFont(30f));
		display.setText(calc.toString());
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		calc.addCalcValueListener(l -> {
			display.setText(l.toString());
		});
		cp.add(display, new RCPosition(1, 1));

		ButtonDigit btnDigit0 = new ButtonDigit(calc, 0);
		cp.add(btnDigit0, new RCPosition(5, 3));

		ButtonDigit btnDigit1 = new ButtonDigit(calc, 1);
		cp.add(btnDigit1, new RCPosition(4, 3));

		ButtonDigit btnDigit2 = new ButtonDigit(calc, 2);
		cp.add(btnDigit2, new RCPosition(4, 4));

		ButtonDigit btnDigit3 = new ButtonDigit(calc, 3);
		cp.add(btnDigit3, new RCPosition(4, 5));

		ButtonDigit btnDigit4 = new ButtonDigit(calc, 4);
		cp.add(btnDigit4, new RCPosition(3, 3));

		ButtonDigit btnDigit5 = new ButtonDigit(calc, 5);
		cp.add(btnDigit5, new RCPosition(3, 4));

		ButtonDigit btnDigit6 = new ButtonDigit(calc, 6);
		cp.add(btnDigit6, new RCPosition(3, 5));

		ButtonDigit btnDigit7 = new ButtonDigit(calc, 7);
		cp.add(btnDigit7, new RCPosition(2, 3));

		ButtonDigit btnDigit8 = new ButtonDigit(calc, 8);
		cp.add(btnDigit8, new RCPosition(2, 4));

		ButtonDigit btnDigit9 = new ButtonDigit(calc, 9);
		cp.add(btnDigit9, new RCPosition(2, 5));

		ButtonBinaryOperator btnSum = new ButtonBinaryOperator(calc, Double::sum, "+");
		cp.add(btnSum, new RCPosition(5, 6));

		ButtonBinaryOperator btnSub = new ButtonBinaryOperator(calc, (a, b) -> a - b, "-");
		cp.add(btnSub, new RCPosition(4, 6));

		ButtonBinaryOperator btnMul = new ButtonBinaryOperator(calc, (a, b) -> a * b, "*");
		cp.add(btnMul, new RCPosition(3, 6));

		ButtonBinaryOperator btnDiv = new ButtonBinaryOperator(calc, (a, b) -> a / b, "/");
		cp.add(btnDiv, new RCPosition(2, 6));

		ButtonExponentOperator btnPot = new ButtonExponentOperator(calc, Math::pow, (x, n) -> Math.pow(x, 1.f / n),
				"x^n", "x^(1/n)");
		cp.add(btnPot, new RCPosition(5, 1));

		JButton btnEqu = new ButtonCalc("=");
		btnEqu.addActionListener(l -> {
			double result = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue());
			calc.setValue(result);
			calc.clearActiveOperand();
			calc.setPendingBinaryOperation(null);
		});
		cp.add(btnEqu, new RCPosition(1, 6));

		JButton btnSwap = new ButtonCalc("+/-");
		btnSwap.addActionListener(l -> {
			calc.swapSign();
		});
		cp.add(btnSwap, new RCPosition(5, 4));

		JButton btnPoint = new ButtonCalc(".");
		btnPoint.addActionListener(l -> {
			calc.insertDecimalPoint();
		});
		cp.add(btnPoint, new RCPosition(5, 5));

		JButton btnClr = new ButtonCalc("clr");
		btnClr.addActionListener(l -> {
			calc.clear();
		});
		cp.add(btnClr, new RCPosition(1, 7));

		JButton btnRes = new ButtonCalc("res");
		btnRes.addActionListener(l -> {
			calc.clearAll();
		});
		cp.add(btnRes, new RCPosition(2, 7));

		JButton btnPush = new ButtonCalc("push");
		btnPush.addActionListener(l -> {
			stack.push(calc.getValue());
		});
		cp.add(btnPush, new RCPosition(3, 7));

		JButton btnPop = new ButtonCalc("pop");
		btnPop.addActionListener(l -> {
			if (stack.isEmpty()) {
				throw new CalculatorInputException("Stog je prazan!");
			}
			calc.freezeValue(null);
			calc.setValue(stack.pop());
		});
		cp.add(btnPop, new RCPosition(4, 7));

		ButtonUnaryOperator btnSin = new ButtonUnaryOperator(calc, Math::sin, Math::asin, "sin", "asin");
		cp.add(btnSin, new RCPosition(2, 2));

		ButtonUnaryOperator btnCos = new ButtonUnaryOperator(calc, Math::cos, Math::acos, "cos", "acos");
		cp.add(btnCos, new RCPosition(3, 2));

		ButtonUnaryOperator btnTan = new ButtonUnaryOperator(calc, Math::tan, Math::atan, "tan", "atan");
		cp.add(btnTan, new RCPosition(4, 2));

		ButtonUnaryOperator btnCtg = new ButtonUnaryOperator(calc, d -> 1f / Math.tan(d),
				d -> Math.PI / 2 - Math.atan(d), "ctg", "actg");
		cp.add(btnCtg, new RCPosition(5, 2));

		ButtonUnaryOperator btnRec = new ButtonUnaryOperator(calc, d -> 1f / d, null, "1/x", null);
		cp.add(btnRec, new RCPosition(2, 1));

		ButtonUnaryOperator btnLog = new ButtonUnaryOperator(calc, Math::log10, d -> Math.pow(10, d), "log", "10^");
		cp.add(btnLog, new RCPosition(3, 1));

		ButtonUnaryOperator btnLn = new ButtonUnaryOperator(calc, Math::log, d -> Math.pow(Math.E, d), "ln", "e^");
		cp.add(btnLn, new RCPosition(4, 1));

		JCheckBox chkInv = new JCheckBox();
		chkInv.setText("Inv");
		chkInv.setBackground(new Color(204, 229, 255));
		chkInv.addActionListener(l -> {
			btnSin.inverse();
			btnCos.inverse();
			btnTan.inverse();
			btnCtg.inverse();
			btnLog.inverse();
			btnLn.inverse();
			btnPot.inverse();
		});
		cp.add(chkInv, new RCPosition(5, 7));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame calculator = new Calculator();
			calculator.pack();
			calculator.setVisible(true);
		});
	}
}
