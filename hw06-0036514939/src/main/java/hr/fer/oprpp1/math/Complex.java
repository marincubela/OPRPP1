package hr.fer.oprpp1.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa koja predstavlja kompleksni broj i pruža podršku za operacije nad
 * kompleksnim brojevima.
 * 
 * @author Cubi
 *
 */
public class Complex {
	private double re;
	private double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Pretpostavljeni konstruktor za klasu {@link Complex} koji inicijalizira
	 * kompleksni broj na 0.
	 */
	public Complex() {
		re = im = 0;
	}

	/**
	 * Konstruktor za klasu {@link Complex}. Prima dva argumenta koji predstavljaju
	 * realni i imaginarni dio kompleksnog broja.
	 * 
	 * @param re realni dio kompleksnog broja.
	 * @param im imaginarni dio kompleksnog broja.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Metoda koja vraća apsolutnu vrijednost ovog kompleksnog broja.
	 * 
	 * @return apsolutnu vrijednost ovog kompleksnog broja.
	 */
	public double module() {
		return Math.sqrt(this.re * this.re + this.im * this.im);
	}

	/**
	 * Metoda koja vraća kut ovog kompleksnog broja zapisanog u polarnom obliku.
	 * Uzimaju se samo kutovi [0, 2 * PI>. Za kompleksni broj 0, kut je neodređen i
	 * metoda vraća NaN.
	 * 
	 * @return kut ovog kompleksnog broja zapisanog u polarnom obliku.
	 */
	public double angle() {
		double angle;
		if (this.re == 0. && this.im == 0.) {
			return Double.NaN;
		} else if (this.re == 0.) {
			if (this.im > 0.)
				angle = Math.PI / 2.;
			else
				angle = -Math.PI / 2;
		} else if (this.im == 0.) {
			if (this.re > 0.)
				angle = 0.;
			else
				angle = Math.PI;
		} else {
			if (this.re > 0.) {
				angle = Math.atan(this.im / this.re);
			} else {
				angle = Math.PI + Math.atan(this.im / this.re);
			}
		}

		if (angle < 0.)
			angle += Math.PI * 2.;

		return angle;
	}

	/**
	 * Metoda koja množi dani kompleksni broj ovim kompleksnim brojem i vraća novi
	 * kompleksni broj koji je umnožak ta dva.
	 * 
	 * @param c kompleksni broj kojim množimo ovaj kompleksni broj.
	 * @return umnožak ovog i danog kompleksnog broja.
	 * @throws NullPointerException baca se ako je dani kompleksni broj
	 *                              <code>null</code>
	 */
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}

		double real = this.re * c.getReal() - this.im * c.getImaginary();
		double imaginary = this.re * c.getImaginary() + this.im * c.getReal();
		return new Complex(real, imaginary);
	}

	/**
	 * Metoda koja dijeli ovaj kompleksni broj danim kompleksnim brojem i vraća novi
	 * kompleksni broj koji je količnik ova dva.
	 * 
	 * @param c kompleksni broj kojim dijelimo ovaj kompleksni broj.
	 * @return količnik ovog i danog kompleksnog broja.
	 * @throws NullPointerException baca se ako je dani kompleksni broj
	 *                              <code>null</code>
	 */
	public Complex divide(Complex c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}

		double denominator = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		double real = this.re * c.getReal() + this.im * c.getImaginary();
		double imaginary = -this.re * c.getImaginary() + this.im * c.getReal();

		return new Complex(real / denominator, imaginary / denominator);
	}

	/**
	 * Metoda koja zbraja dva kompleksna broja i vraća novi kompleksni broj koji je
	 * zbroj ovog kompleksnog broja i predanog.
	 * 
	 * @param c kompleksni broj koji dodajemo ovom kompleksnom broju
	 * @return zbroj ovog i danog kompleksnog broja.
	 * @throws NullPointerException baca se ako je dani kompleksni broj
	 *                              <code>null</code>
	 */
	public Complex add(Complex c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}
		return new Complex(this.re + c.getReal(), this.im + c.getImaginary());
	}

	/**
	 * Metoda koja oduzima dva kompleksna broja i vraća novi kompleksni broj koji je
	 * razlika ovog kompleksnog broja i predanog.
	 * 
	 * @param c kompleksni broj koji oduzimamo ovom kompleksnom broju
	 * @return razlika ovog i danog kompleksnog broja.
	 * @throws NullPointerException baca se ako je dani kompleksni broj
	 *                              <code>null</code>
	 */
	public Complex sub(Complex c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}

		return new Complex(this.re - c.getReal(), this.im - c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj pomnožen s -1.
	 * 
	 * @return novi kompleksni broj pomnožen s -1.
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Metoda koja potencira ovaj kompleksni broj na n-tu potenciju i vraća novi
	 * kompleksni broj koji je rezultat potenciranja.
	 * 
	 * @param n potencija kojom potenciramo ovaj kompleksni broj.
	 * @return potencirani kompleksni broj.
	 * @throws IllegalArgumentException baca se ako je dana potencija manja od 1.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija (n) ne smije biti manja od 0, a n je " + n + "!");
		}

		if (n == 0.) {
			return Complex.ONE;
		}
		Complex result = new Complex(this.re, this.im);

		for (int i = 1; i < n; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	/**
	 * Metoda koja vadi n-ti korijen iz kompleksnog broja. Vraća polje kompleksnih
	 * brojeva koje predstavlja rezultat korijenovanja.
	 * 
	 * @param n korijen koji se vadi iz ovog kompleksnog broja.
	 * @return polje kompleksnih brojeva koja n-ti korijen ovog kompleksnog broja.
	 */
	public List<Complex> root(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Korijen (n) ne smije biti manja od 0, a n je " + n + "!");
		}

		List<Complex> result = new ArrayList<>();

		double magnitude = Math.pow(module(), 1. / (double) n);

		for (int i = 0; i < n; i++) {
			double real, imaginary;
			real = magnitude * Math.cos((this.angle() + i * 2 * Math.PI) / (double) n);
			imaginary = magnitude * Math.sin((this.angle() + i * 2 * Math.PI) / (double) n);

			Complex c = new Complex(real, imaginary);
			result.add(c);
		}

		return result;
	}

	/**
	 * Metoda koja generira novi kompleksni broj iz stringa.
	 * 
	 * @param line dani string koji se pretvara u kompleksni broj.
	 * @return kompleksni broj generiran iz stringa.
	 * @throws IllegalArgumentException baca se ako se string nije uspio parsirati u kompleksni broj.
	 */
	public static Complex fromString(String line) {
		double re = 0, im = 0;
		line = line.trim();
		if (line.isEmpty()) {
			throw new IllegalArgumentException("Prazan string se ne može parsirati u kompleksni broj!");
		}

		// slucajevi gdje je i sam bez broja
		if (line.equals("i")) {
			return new Complex(0, 1);
		}

		if (line.startsWith("-") && line.substring(1).trim().equals("i")) {
			return new Complex(0, -1);
		}

		if (!line.contains("i")) { // a+i0
			return new Complex(Double.parseDouble(line), 0);
		}

		// izbaci vodeci plus
		if (line.startsWith("+")) {
			line = line.substring(1);
		}

		// 0-ib i 0+ib
		if (line.startsWith("i") || (line.startsWith("-") && line.substring(1).trim().startsWith("i"))) {
			if (line.startsWith("-")) {
				im = -Double.parseDouble(line.substring(line.indexOf("i") + 1));
			} else {
				im = Double.parseDouble(line.substring(line.indexOf("i") + 1));
			}
			return new Complex(0, im);
		}

		if (line.contains("+")) { // -a+ib i a+ib
			String[] split = line.split("\\+");
			re = Double.parseDouble(split[0]);
			if (split[1].trim().equals("i")) {
				im = 1;
			} else {
				im = Double.parseDouble(split[1].trim().substring(1));
			}
			return new Complex(re, im);

		} else if (!line.startsWith("-") && line.contains("-")) { // a-ib
			String[] split = line.split("-");
			re = Double.parseDouble(split[0]);
			if (split[1].trim().equals("i")) {
				im = -1;
			} else {
				im = -Double.parseDouble(split[1].trim().substring(1));
			}
			return new Complex(re, im);

		} else if (line.startsWith("-") && line.substring(1).contains("-")) { // -a-ib
			re = Double.parseDouble(line.substring(0, line.substring(1).indexOf("-") + 1));
			im = -Double.parseDouble(line.split("i")[1]);
			return new Complex(re, im);
		}

		throw new IllegalArgumentException("Dani string " + line + " se ne može parsirati!");
	}

	@Override
	public String toString() {
		String real = Double.toString(this.re);
		String imaginary = new String("");

		if (this.im >= 0) {
			imaginary += "+";
		}
		imaginary += Double.toString(this.im) + "i";

		return "(" + real + imaginary + ")";
	}

	/**
	 * Metoda koja vraća realni dio ovog kompleksnog broja.
	 * 
	 * @return realni dio ovog kompleksnog broja.
	 */
	public double getReal() {
		return this.re;
	}

	/**
	 * Metoda koja vraća imaginarni dio ovog kompleksnog broja.
	 * 
	 * @return imaginarni dio ovog kompleksnog broja.
	 */
	public double getImaginary() {
		return this.im;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}
}
