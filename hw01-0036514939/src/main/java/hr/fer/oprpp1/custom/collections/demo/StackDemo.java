package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			throw new IllegalArgumentException("""
					Programu je potrebno predati samo jedan izraz okružen navodnicima pr. \"-1 8 2 / + \".
					Brojevi trebaju biti odmaknuti bar jednim razmakom, a unarni operatori +/- trebaju biti uz brojeve.
					""");
		}

		String input = args[0];
		ObjectStack stack = new ObjectStack();
		var a = parseInput(input);

		for (Object str : a.toArray()) {
			String s = (String) str;
			if (isInt(s)) {
				stack.push(Integer.parseInt(s));
			} else {
				int y = (int) stack.pop();
				int x = (int) stack.pop();
				stack.push(doOperation(x, y, s));
			}
		}

		if (stack.size() != 1) {
			throw new Exception("Program nije uspješno završio. Molimo provjerite izraz!");
		} else {
			System.out.println(stack.pop());
		}

	}

	private static ArrayIndexedCollection parseInput(String input) {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		String[] inputs = input.split("\\s+");

		for (String s : inputs) {
			if (isInt(s) || isOperator(s)) {
				a.add(s);
			} else {
				throw new IllegalArgumentException(
						"Znak " + s + " nije dopušten u izrazu! Izraz " + input + " nije valjan!");
			}
		}

		return a;
	}

	private static boolean isInt(String s) {
		if (s == null) {
			return false;
		}

		int i = 0;
		if (s.charAt(i) == '-' || s.charAt(i) == '+') {
			i++;
			if (s.length() < 2) {
				return false;
			}
		}

		for (; i < s.length(); i++) {
			if (s.charAt(i) < '0' || s.charAt(i) > '9')
				return false;
		}

		return true;
	}

	private static boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*") || s.equals("%");
	}

	private static int doOperation(int x, int y, String s) {
		int result;
		switch (s) {
		case "+":
			result = x + y;
			break;
		case "-":
			result = x - y;
			break;
		case "*":
			result = x * y;
			break;
		case "/":
			result = x / y;
			break;
		case "%":
			result = x % y;
			break;
		default:
			throw new IllegalArgumentException("Neočekivana vrijednost: " + s);
		}

		return result;
	}
}
