package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;

/**
 * Klasa koja modelira akciju pomaka kornjače bez crtanja za danu duljinu.
 * 
 * @author Cubi
 *
 */
public class SkipCommand implements Command {
	private double step;

	/**
	 * Konstruktor za klasu {@link SkipCommand} koji inicijalizira sve potrebne
	 * vrijednosti.
	 * 
	 * @param step duljina pomaka kornjače.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D start = ctx.getCurrentState().getPosition();
		Vector2D end = ctx.getCurrentState().getDirection()
				.scaled(step * ctx.getCurrentState().getEffectiveLength())
				.added(start);

		ctx.getCurrentState().setPosition(end);
	}

}
