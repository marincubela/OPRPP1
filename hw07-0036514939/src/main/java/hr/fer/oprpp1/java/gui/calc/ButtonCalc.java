package hr.fer.oprpp1.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

public class ButtonCalc extends JButton {
	private static final long serialVersionUID = 1L;
	
	public ButtonCalc(String text) {
		super();
		setText(text);
		setBackground(new Color(204, 229, 255));
	}

}
