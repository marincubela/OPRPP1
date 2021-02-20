package hr.fer.oprpp1.math;

/**
 * Klasa koja modelira dvodimenzionalni vektor.
 * 
 * @author Cubi
 *
 */
public class Vector2D {
	private double x;
	private double y;
	
	/**
	 * Konstruktor za klasu {@link Vector2D} koji inicijalizira početne koordinate.
	 * 
	 * @param x vrijednost apscise ovog vektora
	 * @param y vrijednost ordinate ovog vektora
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Metoda koja vraća vrijednost apscise ovog vektora.
	 * 
	 * @return vrijednost apscise ovog vektora.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Metoda koja vraća vrijednost ordinate ovog vektora.
	 * 
	 * @return vrijednost ordinate ovog vektora.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Metoda koja dodaje dani vektor na ovaj vektor.
	 * 
	 * @param offset vektor za koji se uvećava ovaj vektor.
	 */
	public void add(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}
	
	/**
	 * Metoda koja vraća novi vektor koji je zbroj danog i ovog vektora.
	 * 
	 * @param offset vektor koji se dodaje ovom vektoru.
	 * @return novi vektor koji je zbroj ovog i danog vektora.
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
	}
	
	/**
	 * Metoda koja rotira ovaj vektor za dani kut. Kut se mjeri u radijanima.
	 * 
	 * @param angle kut za koji se ovaj vektor rotira.
	 */
	public void rotate(double angle) {
		double oldX = this.x, oldY = this.y;
		this.x = Math.cos(angle) * oldX - Math.sin(angle) * oldY;
		this.y = Math.sin(angle) * oldX + Math.cos(angle) * oldY;
	}
	
	/**
	 * Metoda koja vraća novi vektor koji je dobiven rotacijom ovog vektora za dani kut. 
	 * Kut se mjeri u radijanima.
	 * 
	 * @param angle kut za koji se ovaj vektor rotira.
	 * @return novi vektor koji je dobiven rotacijom ovog vektora za dani kut.
	 */
	public Vector2D rotated(double angle) {
		double newX = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
		double newY = Math.sin(angle) * this.x + Math.cos(angle) * this.y;
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Metoda koja skalira ovaj vektor za danu vrijednost.
	 * 
	 * @param scaler vrijednost kojom se ovaj vektor skalira.
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Metoda koja vraća novi vektor koji je dobiven skaliranjem ovog vektora za danu vrijednost.
	 * 
	 * @param scaler vrijednost kojom se ovaj vektor skalira
	 * @return novi vektor koji je dobiven skaliranjem ovog vektora za danu vrijednost.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * Metoda koja vraća novi vektor koji ima iste vrijednosti kao i ovaj vektor.
	 * 
	 * @return novi vektor s jednakim vrijednostima ovom vektoru.
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
