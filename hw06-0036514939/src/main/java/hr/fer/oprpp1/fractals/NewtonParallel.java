package hr.fer.oprpp1.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class NewtonParallel {

	public static class PosaoIzracuna implements Runnable {
		private final double convergenceTreshold = 0.001;
		private final double rootConvergenceTreshold = 0.002;

		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial cpr;

		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

		private PosaoIzracuna() {
		}

		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial cpr) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.cpr = cpr;
		}

		@Override
		public void run() {
			int offset = yMin * width;

			ComplexPolynomial polynomial = cpr.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();

			for (int y = yMin; y <= yMax; y++) {
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
					} while (module > convergenceTreshold && iters < m);

					data[offset] = (short) (cpr.indexOfClosestRootFor(zn, rootConvergenceTreshold) + 1);
					offset++;
				}
			}
		}
	}

	public static class MojProducer implements IFractalProducer {
		private final int MAX_ITERATIONS = 16 * 16 * 16;
		private final int BROJ_TRAKA;
		private final int BROJ_DRETVI;

		private ComplexRootedPolynomial cpr;

		public MojProducer(int workers, int brojTraka, Complex... roots) {
			cpr = new ComplexRootedPolynomial(Complex.ONE, roots);
			BROJ_DRETVI = workers;
			BROJ_TRAKA = brojTraka;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			System.out.println("Broj dretvi: " + BROJ_DRETVI);
			System.out.println("Broj poslova: " + BROJ_TRAKA);

			short[] data = new short[width * height];
			int brojTraka = height > BROJ_TRAKA ? BROJ_TRAKA : height;
			int brojYPoTraci = height / brojTraka;

			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[BROJ_DRETVI];
			for (int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if (p == PosaoIzracuna.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}

			for (int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;

				if (i == brojTraka - 1) {
					yMax = height - 1;
				}

				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax,
						MAX_ITERATIONS, data, cancel, cpr);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (cpr.toComplexPolynom().order() + 1), requestNo);
		}
	}

	public static void main(String[] args) {
		int N = Runtime.getRuntime().availableProcessors();
		int K = N * 4;
		boolean nSet = false;
		boolean kSet = false;

		for (int i = 0; i < args.length; i++) {
			String s = args[i];

			if (s.startsWith("--workers=")) {
				if (!nSet) {
					nSet = true;
					N = Integer.parseInt(s.split("=")[1]);
				} else {
					throw new IllegalArgumentException("Parameter workers can only be set one time!");
				}
			} else if (s.startsWith("--tracks=")) {
				if (!kSet) {
					kSet = true;
					K = Integer.parseInt(s.split("=")[1]);
				} else {
					throw new IllegalArgumentException("Parameter workers can only be set one time!");
				}
				N = Integer.parseInt(s.split("=")[1]);
			} else if (s.equals("-w")) {
				if (!nSet) {
					nSet = true;
					N = Integer.parseInt(args[++i]);
				} else {
					throw new IllegalArgumentException("Parameter workers can only be set one time!");
				}
			} else if (s.equals("-t")) {
				if (!kSet) {
					kSet = true;
					K = Integer.parseInt(args[++i]);
				} else {
					throw new IllegalArgumentException("Parameter workers can only be set one time!");
				}
			}
		}

		if (N < 1 || K < 1) {
			throw new IllegalArgumentException("Parameters can't be negative!");
		}

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
		for (int i = 0; i < roots.size(); i++) {
			arrayRoots[i] = roots.get(i);
		}

		FractalViewer.show(new MojProducer(N, K, arrayRoots));
	}
}
