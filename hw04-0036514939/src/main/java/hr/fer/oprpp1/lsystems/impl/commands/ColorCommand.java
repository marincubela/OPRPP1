package hr.fer.oprpp1.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Klasa koja modelira akciju promjene boje za kornjaču sa vrha stoga konteksta.
 * 
 * @author Cubi
 *
 */
public class ColorCommand implements Command {
	private Color color;

	/**
	 * Konstruktor za klasu {@link ColorCommand} koji inicijalizira sve potrebne
	 * vrijednosti.
	 * 
	 * @param color nova boja kojom će kornjača sa vrha stoga konteksta crtati.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
