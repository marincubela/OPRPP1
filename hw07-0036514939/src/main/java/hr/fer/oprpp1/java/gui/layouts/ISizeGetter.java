package hr.fer.oprpp1.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

@FunctionalInterface
public interface ISizeGetter {
	public Dimension get(Component c);
}
