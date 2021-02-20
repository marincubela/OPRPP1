package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class ExitShellCommand implements ShellCommand {
	private final String name = "exit";
	private final List<String> description;

	public ExitShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to exit the shell.");
		
		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isBlank()) {
			return ShellStatus.TERMINATE;
		}

		throw new IllegalArgumentException("Exit command must have no arguments, but had arguments " + arguments + "!");
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
