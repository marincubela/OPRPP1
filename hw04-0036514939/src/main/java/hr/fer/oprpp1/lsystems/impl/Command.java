package hr.fer.oprpp1.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje koje modelira jednu akciju kornjače.
 * 
 * @author Cubi
 *
 */
public interface Command {
	/**
	 * Metoda koja izvršava definiranu akciju.
	 * 
	 * @param ctx kontekst u kojem se kornjača nalazi.
	 * @param painter painter kojim će kornjača crtati linije po površini prozora.
	 */
	void execute(Context ctx, Painter painter);
}
