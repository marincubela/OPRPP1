package hr.fer.oprpp1.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Klasa koja predstavlja kontekst u kojem se kornjača nalazi i pamti sva stanja kornjače.
 * 
 * @author Cubi
 *
 */
public class Context {
	private ObjectStack<TurtleState> turtleStack;

	/**
	 * Pretpostavljeni konstruktor za klasu {@link Context} koja inicijalizira stog
	 * na kojem se spremaju stanja kornjače.
	 */
	public Context() {
		this.turtleStack = new ObjectStack<>();
	}

	/**
	 * Metoda koja vraća stanje s vrha stoga bez uklanjanja.
	 * 
	 * @return stanje s vrha stoga bez uklanjanja.
	 */
	public TurtleState getCurrentState() {
		return this.turtleStack.peek();
	}

	/**
	 * Metoda koja na vrh gura predano stanje.
	 * 
	 * @param state stanje koje se gura na vrh stoga.
	 */
	public void pushState(TurtleState state) {
		this.turtleStack.push(state);
	}

	/**
	 * Metoda koja briše jedno stanje s vrha.
	 */
	public void popState() {
		this.turtleStack.pop();
	}
}
