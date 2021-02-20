package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;

/**
 * Klasa koja modelira akciju crtanja linije dane duljine.
 * 
 * @author Cubi
 *
 */
public class DrawCommand implements Command {
	private double step;

	/**
	 * Konstruktor za klasu {@link DrawCommand} koji inicijalizira sve potrebne
	 * vrijednosti.
	 * 
	 * @param step duljina pomaka kornjače.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D start = ctx.getCurrentState().getPosition();
		Vector2D end = ctx.getCurrentState().getDirection()
				.scaled(step * ctx.getCurrentState().getEffectiveLength())
				.added(start);

		painter.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), ctx.getCurrentState().getColor(), 1f);
		ctx.getCurrentState().setPosition(end);
	}

}
