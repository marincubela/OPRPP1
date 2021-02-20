package hr.fer.oprpp1.math;

/**
 * Klasa koja predstavlja polinom s kompleksnim koeficijentima prikazan u obliku
 * s potencijama.
 * 
 * @author Cubi
 *
 */
public class ComplexPolynomial {
	private Complex[] factors;

	/**
	 * Konstruktor za klasu {@link ComplexPolynomial} koji prima koeficijente koji
	 * se nalaze uz potencije polinoma.
	 * 
	 * @param factors koeficijenti uz potencije polinoma.
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Metoda koja vraća stupanj polinoma.
	 * 
	 * @return stupanj polinoma.
	 */
	public short order() {
		if (factors.length <= 1) {
			return 0;
		}

		return (short) (factors.length - 1);
	}

	/**
	 * Metoda koja množi ovaj polinom danim polinomom i vraća novi polinom koji je
	 * umnožak dva polinoma.
	 * 
	 * @param p dani polinom.
	 * @return novi polinom koji je umnožak ovog i danog polinoma.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] factors = new Complex[this.order() + p.order() + 1];
		
		for(int i = 0; i < factors.length; i++) {
			factors[i] = Complex.ZERO;
		}

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				factors[i + j] = factors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Metoda koja vraća prvu derivaciju ovog polinoma kao novi polinom.
	 * 
	 * @return novi polinom koji je prva derivacija ovog polinoma.
	 */
	public ComplexPolynomial derive() {
		Complex[] factors = new Complex[this.factors.length - 1];

		for (int i = 1; i < this.factors.length; i++) {
			factors[i - 1] = this.factors[i].multiply(new Complex(i, 0));
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Metoda koja izračunava vrijednost polinoma za dani kompleksni broj z.
	 * 
	 * @param z argument polinoma.
	 * @return vrijednost polinom za dani kompleksni broj z.
	 */
	public Complex apply(Complex z) {
		Complex c = Complex.ZERO;

		for (int i = 0; i < this.factors.length; i++) {
			c = c.add(z.power(i).multiply(this.factors[i]));
		}

		return c;
	}

	@Override
	public String toString() {
		String res = new String("");

		for (int i = factors.length - 1; i >= 0; i--) {
			res += factors[i].toString();
			if (i != 0) {
				res += "*z^" + i + "+";
			}
		}

		return res;
	}
}
