package hr.fer.oprpp1.math;

/**
 * Klasa koja predstavlja polinom s kompleksnim koeficijentima prikazan u
 * faktoriziranom obliku.
 * 
 * @author Cubi
 *
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;

	/**
	 * Konstruktor koji prima vodeći koeficijent i nultočke polinoma kao parametre
	 * za inicijalizaciju.
	 * 
	 * @param constant vodeći koeficijent polinoma.
	 * @param roots    nultočke polinoma.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Metoda koja izračunava vrijednost polinoma za dani kompleksni broj z.
	 * 
	 * @param z argument polinoma.
	 * @return vrijednost polinom za dani kompleksni broj z.
	 */
	public Complex apply(Complex z) {
		Complex c = constant;

		for (Complex root : roots) {
			c = c.multiply(z.sub(root));
		}

		return c;
	}

	/**
	 * Pretvara kompleksni polinom iz faktoriziranog oblika u oblik s potencijama.
	 * 
	 * @return kompleksni polinom zapisan u obliku s potencijama.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial res = new ComplexPolynomial(constant);

		for (Complex root : roots) {
			ComplexPolynomial cp = new ComplexPolynomial(root, Complex.ONE);
			res = res.multiply(cp);
		}

		return res;
	}

	@Override
	public String toString() {
		String res = constant.toString();

		for (Complex root : roots) {
			res += "*(z-" + root.toString() + ")";
		}

		return res;
	}

	/**
	 * Metoda koja traži najbližu nultočku danom kompleksnom broju koja nije
	 * udaljena više od danog praga i vraća njen index. Ako nijedna nultočka ne
	 * zadovoljava uvjet, metoda vraća -1.
	 * 
	 * @param z        dani kompleksni broj.
	 * @param treshold prag udaljenosti.
	 * @return index nultočke koja je najbliža danom kompleksnom broju z
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double distance = treshold;

		for (int i = 0; i < roots.length; i++) {
			double d = z.sub(roots[i]).module();
			if (distance > d) {
				index = i;
				distance = d;
			}
		}

		return index;
	}
}
