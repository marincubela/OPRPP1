package hr.fer.oprpp1.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

import hr.fer.oprpp1.java.gui.calc.model.CalcModel;
import hr.fer.oprpp1.java.gui.calc.model.CalcValueListener;
import hr.fer.oprpp1.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {
	private boolean isEditable;
	private boolean isPositive;
	private String digits;
	private double value;
	private String freezeValue;
	private OptionalDouble activeOperand;
	private DoubleBinaryOperator pendingOperator;
	private List<CalcValueListener> listeners;

	public CalcModelImpl() {
		isEditable = true;
		isPositive = true;
		digits = new String("");
		value = 0;
		freezeValue = null;
		activeOperand = OptionalDouble.empty();
		pendingOperator = null;
		listeners = new ArrayList<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	private void notifyListeners() {
		for(CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		this.digits = Double.toString(value);
		this.isEditable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isPositive = true;
		value = 0;
		digits = new String("");
		isEditable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		this.clear();
		freezeValue = null;
		clearActiveOperand();
		pendingOperator = null;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("Nije moguće promijeniti predznak, string nije editabilan!");
		}

		value = -value;
		if (isPositive) {
			digits = "-" + digits;	
		} else {
			digits = digits.substring(1);
		}
		isPositive = !isPositive;
		freezeValue(null);
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("Nije moguće unijeti znamenku, string nije editabilan!");
		}

		if (digits.contains(".")) {
			throw new CalculatorInputException("Decimalna točka je već unesena!");
		}

		if (digits.isEmpty() || digits.equals("-")) {
			throw new CalculatorInputException("Decimalna točka ne može biti na prvom mjestu!");
		}

		digits += ".";
		freezeValue(null);
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException("Nije moguće unijeti znamenku, string nije editabilan!");
		}
		
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Dopušteno je unijeti samo jednu znamenku, a pokušano je unijeti " + digit + "!");
		}

		String newString = digits;
		try {
			if(digits.isEmpty()) {
				digits += digit;
				double newValue = Double.parseDouble(digits);
				value = newValue;
			} else if (digits.equals("0")) {
				digits = Integer.toString(digit);
				value = digit;
			} else if(digits.equals("-0")) {
				digits = "-" + digit;
				double newValue = Double.parseDouble(digits);
				value = newValue;
			} else if (!(digits.equals("0") && digit == 0)) {
				newString += digit;
				double newValue = Double.parseDouble(newString);
				if(!Double.isFinite(newValue)) {
					throw new CalculatorInputException("Nije moguće dodati još jednu znamenku. Previše znamenki je unešeno!");
				}
				value = newValue;
				digits = newString;
			}

		} catch (Exception e) {
			throw new CalculatorInputException(e.getMessage());
		}
		freezeValue(null);
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand.isPresent();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Operand nije postavljen!");
		}

		return activeOperand.getAsDouble();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = OptionalDouble.empty();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperator = op;
	}

	public void freezeValue(String value) {
		freezeValue = value;
	}
	
	public boolean hasFrozenValue() {
		return freezeValue != null;
	}
	
	@Override
	public String toString() {
		if (!hasFrozenValue()) {
			if(digits.isEmpty() || digits.equals("-")) {
				if (isPositive) {
					return new String("0");
				} else {
					return new String("-0");
				}
			} else {
				return digits;
			}
		}
		
		return freezeValue;
	}
}
