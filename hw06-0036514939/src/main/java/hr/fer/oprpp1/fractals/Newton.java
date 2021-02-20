package hr.fer.oprpp1.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class Newton {
	public static class MojProducer implements IFractalProducer {
		private final double convergenceTreshold = 0.001;
		private final double rootConvergenceTreshold = 0.002;
		private final int MAX_ITERATIONS = 16 * 16 * 16;

		private ComplexRootedPolynomial cpr;

		public MojProducer(Complex... roots) {
			cpr = new ComplexRootedPolynomial(Complex.IM_NEG, roots);
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int offset = 0;

			ComplexPolynomial polynomial = cpr.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();

			short[] data = new short[width * height];
			for (int y = 0; y < height; y++) {
				if (cancel.get())
					break;

				for (int x = 0; x < width; x++) {
					double re = x / (width - 1.0) * (reMax - reMin) + reMin;
					double im = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(re, im);
					Complex znold;
					Complex numerator, denominator, fraction;
					double module;
					int iters = 0;

					do {
						numerator = polynomial.apply(zn);
						denominator = derived.apply(zn);
						znold = zn;
						fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while (module > convergenceTreshold && iters < MAX_ITERATIONS);

					data[offset] = (short) (cpr.indexOfClosestRootFor(zn, rootConvergenceTreshold) + 1);
					offset++;
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new ArrayList<>();

		try (Scanner sc = new Scanner(System.in)) {
			String line;
			do {
				System.out.format("Root %d>", roots.size() + 1);
				while (!(line = sc.nextLine()).equals("done")) {
					roots.add(Complex.fromString(line));
					System.out.format("Root %d>", roots.size() + 1);
				}
			} while (roots.size() < 2);
		}
		Complex[] arrayRoots = new Complex[roots.size()];
		for(int i = 0; i < roots.size(); i++) {
			arrayRoots[i] = roots.get(i);
		}
		FractalViewer.show(new MojProducer(arrayRoots));
	}
}
