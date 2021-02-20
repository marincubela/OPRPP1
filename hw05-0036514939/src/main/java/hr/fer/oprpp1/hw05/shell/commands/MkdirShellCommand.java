package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.utils.Util;

public class MkdirShellCommand implements ShellCommand {
	private final String name = "mkdir";
	
	private final List<String> description;

	public MkdirShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to make directory in current directory.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> split = Util.parseArguments(arguments);
		
		if (split.size() != 1) {
			throw new IllegalArgumentException(
					"mkdir command has one argument, but was given " + split.size() + " arguments!");
		}
		
		
		try {
			Path dir = Paths.get(split.get(0));
			Files.createDirectories(dir);
		} catch (IOException e) {
			throw new ShellIOException(e);
		}
			
		env.writeln("Directory " + split.get(0) + " succesfully created");
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
