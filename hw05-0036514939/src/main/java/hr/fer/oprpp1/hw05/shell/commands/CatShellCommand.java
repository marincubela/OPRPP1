package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
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

public class CatShellCommand implements ShellCommand {
	private final String name = "cat";
	private final List<String> description;

	public CatShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command prints the file with a given charset.");
		list.add("If no charset is provided as argument, default charset is used.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> split = Util.parseArguments(arguments);
		if (!(split.size() == 1 || split.size() == 2)) {
			throw new IllegalArgumentException(
					"cat command has one or two arguments, but was given " + split.size() + " arguments!");
		}

		Path file = Paths.get(split.get(0));
		Charset cset = Charset.defaultCharset();
		if (split.size() == 2) {
			cset = Charset.forName(split.get(1));
		}
		try {
			BufferedReader buffReader = Files.newBufferedReader(file, cset);
			String line = null;
			while ((line = buffReader.readLine()) != null) {
				env.writeln(line);
			}
		} catch (MalformedInputException e) {
			throw new ShellIOException("File can't be read with a given charset!");
		} catch (IOException e) {
			throw new ShellIOException(e);
		}

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
