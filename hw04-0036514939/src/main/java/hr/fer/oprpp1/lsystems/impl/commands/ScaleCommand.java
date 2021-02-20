package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Klasa koja modelira akciju skaliranja efektivne duljine pomaka za trenutno
 * stanje kornjače na vrhu stoga konteksta.
 * 
 * @author Cubi
 *
 */
public class ScaleCommand implements Command {
	private double factor;

	/**
	 * Konstruktor za klasu {@link SkipCommand} koji inicijalizira sve potrebne
	 * vrijednosti.
	 * 
	 * @param factor duljina pomaka kornjače.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setEffectiveLength(ctx.getCurrentState().getEffectiveLength() * factor);
	}

}
