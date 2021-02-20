package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class SymbolShellCommand implements ShellCommand {
	private final String name = "symbol";
	
	private final List<String> description;

	public SymbolShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to change symbols and to list wanted symbol.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] split = arguments.split(" ");
		String symbolName = split[0];
		String symbol = split.length == 1 ? "" : split[1];

		String text = new String("Symbol for " + symbolName);

		if (symbolName.equals("PROMPT")) {
			if (symbol.isEmpty()) {
				text += " is '" + env.getPromptSymbol() + "'";
			} else {
				if (symbol.length() > 1) {
					throw new IllegalArgumentException("Symbol must be character, but was " + symbol + "!");
				}
				text += " changed from '" + env.getPromptSymbol() + "' to '" + symbol + "'";
				env.setPromptSymbol(symbol.charAt(0));
			}
		} else if (symbolName.equals("MORELINES")) {
			if (symbol.isEmpty()) {
				text += " is '" + env.getMorelinesSymbol() + "'";
			} else {
				if (symbol.length() > 1) {
					throw new IllegalArgumentException("Symbol must be character, but was " + symbol + "!");
				}
				text += " changed from '" + env.getMorelinesSymbol() + "' to '" + symbol + "'";
				env.setMorelinesSymbol(symbol.charAt(0));
			}
		} else if (symbolName.equals("MULTILINE")) {
			if (symbol.isEmpty()) {
				text += " is '" + env.getMultilineSymbol() + "'";
			} else {
				if (symbol.length() > 1) {
					throw new IllegalArgumentException("Symbol must be character, but was " + symbol + "!");
				}
				text += " changed from '" + env.getMultilineSymbol() + "' to '" + symbol + "'";
				env.setMultilineSymbol(symbol.charAt(0));
			}

		} else {
			throw new IllegalArgumentException(
					"Available symbol names are: PROMPT, MORELINES and MULTILINE, but " + symbolName + "was given!");
		}

		env.writeln(text);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
