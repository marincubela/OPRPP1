package hr.fer.oprpp1.hw05.shell.utils;

import java.util.ArrayList;
import java.util.List;

public class Util {
	public static List<String> parseArguments(String arguments) {
		String str = new String("");
		List<String> args = new ArrayList<>();

		boolean quotes = false;
		boolean escape = false;
		char c;

		for (int i = 0; i < arguments.length(); i++) {
			c = arguments.charAt(i);

			if (!quotes) {
				if (c == '"') {
					quotes = true;
				} else if (c == ' ') {
					args.add(str);
					str = new String("");
				} else {
					str += c;
					if (i == arguments.length() - 1) {
						args.add(str);
						str = null;
					}
				}
			} else {
				if (escape) {
					if (c == '"' || c == '\\') {
						str += c;
					} else {
						str += '\\';
						str += c;
					}
					escape = false;
				} else {
					if (c == '"') {
						if (i == arguments.length() - 1
								|| (i + 1 < arguments.length() && arguments.charAt(i + 1) == ' ')) {
							quotes = false;
							args.add(str);
							str = new String("");
							i++;
						} else {
							throw new IllegalArgumentException(
									"Incorrectly given path or string! Ending a quote must be followed by empty space or be at end of arguments!");
						}
					} else if (c == '\\') {
						escape = true;
					} else {
						str += c;
					}
				}
			}
		}

		return args;
	}
}
