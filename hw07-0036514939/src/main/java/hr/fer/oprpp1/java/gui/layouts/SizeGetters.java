package hr.fer.oprpp1.java.gui.layouts;

public class SizeGetters {
	public static final ISizeGetter PREFERRED_SIZE = c -> c.getPreferredSize();

	public static final ISizeGetter MAXIMUM_SIZE = c -> c.getMaximumSize();

	public static final ISizeGetter MINIMUM_SIZE = c -> c.getMinimumSize();
}
