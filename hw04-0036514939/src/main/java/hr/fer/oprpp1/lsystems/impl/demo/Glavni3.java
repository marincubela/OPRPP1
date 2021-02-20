package hr.fer.oprpp1.lsystems.impl.demo;

import hr.fer.oprpp1.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.gui.LSystemViewer;

public class Glavni3 {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
