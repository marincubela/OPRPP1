package hr.fer.oprpp1.lsystems.impl;

import java.awt.Color;
import hr.fer.oprpp1.math.Vector2D;

/**
 * Klasa koja pamti trenutno stanje kornjače (poziciju, smjer, boju i efektivnu
 * duljinu pomaka)
 * 
 * @author Cubi
 *
 */
public class TurtleState {
	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double effectiveLength;

	/**
	 * Konstruktor za klasu {@link TurtleState} koji inicijalizira sve vrijednosti
	 * ove klase.
	 * 
	 * @param position        trenutna pozicija kornjače.
	 * @param direction       trenutni smjer kornjače.
	 * @param color           trenutna boja kojom kornjača crta.
	 * @param effectiveLength trenuna efektivna duljina pomaka.
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveLength) {
		this.position = position.copy();
		this.direction = direction.copy();
		this.color = color;
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Metoda koja vraća kopiju trenutnu poziciju kornjače.
	 * 
	 * @return trenutnu poziciju kornjače.
	 */
	public Vector2D getPosition() {
		return position.copy();
	}

	/**
	 * Metoda koja postavlja trenutnu poziciju kornjače na ekranu na danu
	 * vrijednost.
	 * 
	 * @param position nova pozicija kornjače.
	 */
	public void setPosition(Vector2D position) {
		this.position = position.copy();
	}

	/**
	 * Metoda koja vraća kopiju trenutnog smjera kornjače.
	 * 
	 * @return trenutni smjer kornjače.
	 */
	public Vector2D getDirection() {
		return direction.copy();
	}

	/**
	 * Metoda koja postavlja trenutni smjer kornjače na danu vrijednost.
	 * 
	 * @param direction novi smjer kornjače.
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction.copy();
	}

	/**
	 * Metoda koja vraća trenutnu boju kojom kornjača crta po ekranu.
	 * 
	 * @return trenutnu boju kojom kornjača crta po ekranu.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Metoda koja postavlja trenutni boju kojom kornjača crta po ekranu.
	 * 
	 * @param color nova boja kojom kornjača crta po ekranu.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Metoda koja vraća trenutnu efektivnu duljinu pomaka kornjače.
	 * 
	 * @return trenutnu efektivnu duljinu pomaka kornjače.
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

	/**
	 * Metoda koja postavlja trenutnu efektivnu duljinu pomaka kornjače.
	 * 
	 * @param effectiveLength nova efektivna duljina pomaka kornjače.
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Metoda koja vraća kopiju trenutnog stanja kornjače kao novi objekt.
	 * 
	 * @return kopiju trenutnog stanja kornjače.
	 */
	public TurtleState copy() {
		return new TurtleState(this.position.copy(), this.direction.copy(), color, effectiveLength);
	}
}
