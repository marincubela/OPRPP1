package hr.fer.oprpp1.ava.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.java.gui.layouts.CalcLayout;
import hr.fer.oprpp1.java.gui.layouts.CalcLayoutException;
import hr.fer.oprpp1.java.gui.layouts.RCPosition;

public class CalcLayoutTest {

	@Test
	public void testRCPositionOutOfBounds() {
		JFrame jframe = new JFrame();
		Container cp = jframe.getContentPane();
		cp.setLayout(new CalcLayout());

		assertThrows(IllegalArgumentException.class, () -> cp.add(new JLabel(), new RCPosition(-1, 2)));
		assertThrows(IllegalArgumentException.class, () -> cp.add(new JLabel(), new RCPosition(1, -2)));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(1, 8)));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(9, 2)));
	}

	@Test
	public void testRCPositionFirstButton() {
		JFrame jframe = new JFrame();
		Container cp = jframe.getContentPane();
		cp.setLayout(new CalcLayout());

		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(1, 4)));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(1, 5)));
	}

	@Test
	public void testAddSameComponent() {
		JFrame jframe = new JFrame();
		Container cp = jframe.getContentPane();
		cp.setLayout(new CalcLayout());
		cp.add(new JLabel(), new RCPosition(1, 6));

		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel(), new RCPosition(1, 6)));
	}
	
	@Test
	public void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(158, dim.height);
		assertEquals(152, dim.width);
	}
}
