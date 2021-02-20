package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Klasa koja modelira akciju rotiranja kornjače za dani kut.
 * 
 * @author Cubi
 *
 */
public class RotateCommand implements Command {
	private double angle;

	/**
	 * Pretpostavljeni konstuktor za klasu {@link RotateCommand} koji inicijalizira
	 * potrebne vrijednosti. Kut u stupnjevima se odmah pretvara u radijane.
	 * 
	 * @param angle kut u stupnjevima za koji će se smjer kornjače na vrhu stoga konteksa zakrenuti.
	 */
	public RotateCommand(double angle) {
		this.angle = angle * Math.PI / 180.f;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setDirection(ctx.getCurrentState().getDirection().rotated(angle));
	}

}
