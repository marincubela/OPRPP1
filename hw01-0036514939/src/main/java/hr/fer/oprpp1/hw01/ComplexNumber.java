package hr.fer.oprpp1.hw01;

/**
 * Klasa koja predstavlja kompleksni broj i pruža podršku za operacije nad
 * kompleksnim brojevima.
 * 
 * @author Cubi
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;

	/**
	 * Konstruktor za klasu {@link ComplexNumber}. Prima dva argumenta koji
	 * predstavljaju realni i imaginarni dio kompleksnog broja.
	 * 
	 * @param real      realni dio kompleksnog broja.
	 * @param imaginary imaginarni dio kompleksnog broja.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Statična metoda koja ustvari radi novu instancu ove klase i vraća ju kao
	 * rezultat. Realan broj zapisuje kao kompleksni broj.
	 * 
	 * @param real realni broj koji pretvaramo u kompleksni zapis.
	 * @return kompleksni broj s imaginarnim dijelom jednakim 0.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.);
	}

	/**
	 * Statična metoda koja ustvari radi novu instancu ove klase i vraća ju kao
	 * rezultat. Imaginaran broj zapisuje kao kompleksni broj.
	 * 
	 * @param imaginary imaginarni broj koji pretvaramo u kompleksni zapis.
	 * @return kompleksni broj s realnim dijelom jednakim 0.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0., imaginary);
	}

	/**
	 * Statična metoda koja iz apsolutne vrijednosti i kuta stvara kompleksni broj i
	 * vraća ga u algebarskom zapisu.
	 * 
	 * @param magnitude apsolutna vrijednost kompleksnog broja.
	 * @param angle     kut kompleksnog broja.
	 * @return kompleksni broj u algebarskom zapisu.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber((magnitude * Math.cos(angle)), (magnitude * Math.sin(angle)));
	}

	/**
	 * Metoda koja zadani string parsira u kompleksni broj i vraća ga.
	 * 
	 * @param s string koji treba parsirati.
	 * @return kompleksni broj pročitan iz stringa.
	 */
	public static ComplexNumber parse(String s) {
		double real = 0., imaginary = 0.;

		// Izbaci početni + ako se nalazi u stringu
		if (s.startsWith("+")) {
			s = s.substring(1);
		}
		
		// Posebni slučajevi za brojeve 1i i +1i
		if (s.equals("i") || s.equals("+i")) {
			return ComplexNumber.fromImaginary(1.);
		}

		if (s.equals("-i")) {
			return ComplexNumber.fromImaginary(-1.);
		}

		// Parsiramo tako da isključujemo jedan po jedan slučaj i to tako da
		// slučaj koji dolazi prije ne sadrži slučaj koji dolazi poslije
		if (s.contains("+")) {	// a+bi
			String[] numbers = s.split("\\+");
			real = Double.parseDouble(numbers[0]);
			if(numbers[1].equals("i")) {	// U slučaju da je imaginarna jedinica sama
				imaginary = 1;
			} else {
				if(!numbers[1].endsWith("i")) {
					throw new IllegalArgumentException();
				}
				imaginary = Double.parseDouble(numbers[1].substring(0, numbers[1].length() - 1));
			}
			return new ComplexNumber(real, imaginary);
		} else if (s.startsWith("-") && s.substring(1).contains("-")) {	//-a-bi
			String[] numbers = s.split("-");
			real = -Double.parseDouble(numbers[1]);
			if(numbers[2].equals("i")) {
				imaginary = -1;
			}else {
				if(!numbers[2].endsWith("i")) {
					throw new IllegalArgumentException();
				}
				imaginary = -Double.parseDouble(numbers[2].substring(0, numbers[2].length() - 1));
			}
			return new ComplexNumber(real, imaginary);

		} else if (s.startsWith("-")) {	// -a ili -bi
			if (s.contains("i")) {// -bi
				imaginary = Double.parseDouble(s.substring(0, s.length() - 1));
				return new ComplexNumber(real, imaginary);
			} else {	// -a
				real = Double.parseDouble(s);
				return new ComplexNumber(real, imaginary);
			}

		} else if (s.contains("-")) {	// a - bi
			String[] numbers = s.split("-");
			real = Double.parseDouble(numbers[0]);
			if(numbers[1].equals("i")) {
				imaginary = -1;
			} else {
				if(!numbers[1].endsWith("i")) {
					throw new IllegalArgumentException();
				}
				imaginary = -Double.parseDouble(numbers[1].substring(0, numbers[1].length() - 1));
			}

			return new ComplexNumber(real, imaginary);
		} else {
			if (s.contains("i")) {	// bi
				double im = Double.parseDouble(s.substring(0, s.length() - 1));
				return ComplexNumber.fromImaginary(im);
			} else {	// a
				return ComplexNumber.fromReal(Double.parseDouble(s));
			}
		}
	}

	/**
	 * Metoda koja vraća realni dio ovog kompleksnog broja.
	 * 
	 * @return realni dio ovog kompleksnog broja.
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Metoda koja vraća imaginarni dio ovog kompleksnog broja.
	 * 
	 * @return imaginarni dio ovog kompleksnog broja.
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Metoda koja vraća apsolutnu vrijednost ovog kompleksnog broja.
	 * 
	 * @return apsolutnu vrijednost ovog kompleksnog broja.
	 */
	public double getMagnitude() {
		return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
	}

	/**
	 * Metoda koja vraća kut ovog kompleksnog broja zapisanog u polarnom obliku.
	 * Uzimaju se samo kutovi [0, 2 * PI>.
	 * Za kompleksni broj 0, kut je neodređen i metoda vraća NaN.
	 * 
	 * @return kut ovog kompleksnog broja zapisanog u polarnom obliku.
	 */
	public double getAngle() {
		double angle;
		if (this.real == 0. && this.imaginary == 0.) {
			return Double.NaN;
		} else if (this.real == 0.) {
			if (this.imaginary > 0.)
				angle = Math.PI / 2.;
			else
				angle = -Math.PI / 2;
		} else if (this.imaginary == 0.) {
			if (this.real > 0.)
				angle = 0.;
			else
				angle = Math.PI;
		} else {
			if (this.real > 0.) {
				angle = Math.atan(this.imaginary / this.real);
			} else {
				angle = Math.PI + Math.atan(this.imaginary / this.real);
			}
		}

		if (angle < 0.)
			angle += Math.PI * 2.;

		return angle;
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
	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
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
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}

		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
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
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}

		double real = this.real * c.getReal() - this.imaginary * c.getImaginary();
		double imaginary = this.real * c.getImaginary() + this.imaginary * c.getReal();
		return new ComplexNumber(real, imaginary);
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
	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("Kompleksni broj ne smije biti null!");
		}

		double denominator = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		double real = this.real * c.getReal() + this.imaginary * c.getImaginary();
		double imaginary = -this.real * c.getImaginary() + this.imaginary * c.getReal();

		return new ComplexNumber(real / denominator, imaginary / denominator);
	}

	/**
	 * Metoda koja potencira ovaj kompleksni broj na n-tu potenciju i vraća novi
	 * kompleksni broj koji je rezultat potenciranja.
	 * 
	 * @param n potencija kojom potenciramo ovaj kompleksni broj.
	 * @return potencirani kompleksni broj.
	 * @throws IllegalArgumentException baca se ako je dana potencija manja od 1.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija (n) ne smije biti manja od 0, a n je " + n + "!");
		}

		if (n == 0.) {
			return ComplexNumber.fromReal(1.);
		}
		ComplexNumber result = new ComplexNumber(this.real, this.imaginary);

		for (int i = 1; i < n; i++) {
			result = result.mul(this);
		}

		return result;
	}

	/**
	 * Metoda koja vadi n-ti korijen iz kompleksnog broja.
	 * Vraća polje kompleksnih brojeva koje predstavlja rezultat korijenovanja.
	 * 
	 * @param n korijen koji se vadi iz ovog kompleksnog broja.
	 * @return polje kompleksnih brojeva koja n-ti korijen ovog kompleksnog broja.
	 */
	public ComplexNumber[] root(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Korijen (n) ne smije biti manja od 0, a n je " + n + "!");
		}

		ComplexNumber[] result = new ComplexNumber[n];

		double magnitude = Math.pow(this.getMagnitude(), 1. / (double) n);

		for (int i = 0; i < n; i++) {
			double real, imaginary;
			real = magnitude * Math.cos((this.getAngle() + i * 2 * Math.PI) / (double) n);
			imaginary = magnitude * Math.sin((this.getAngle() + i * 2 * Math.PI) / (double) n);

			ComplexNumber c = new ComplexNumber(real, imaginary);
			result[i] = c;
		}

		return result;
	}

	@Override
	public String toString() {
		String real = new String("");
		String imaginary = new String("");
		if(this.real != 0) {
			real = Double.toString(this.real);					
		}
		
		if(this.imaginary != 0) {
			if(this.imaginary > 0 && this.real != 0) {
				imaginary += "+";
			}
			imaginary += Double.toString(this.imaginary) + "i";
		}
		
		return real + imaginary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
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
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

}
