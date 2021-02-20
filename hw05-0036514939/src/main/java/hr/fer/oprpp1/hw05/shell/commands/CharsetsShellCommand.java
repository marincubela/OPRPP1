package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {
	private final String name = "charsets";
	private final List<String> description;
	
	public CharsetsShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command returns list of all charsets that this program can support.");
		
		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for(var c : Charset.availableCharsets().keySet()) {
			env.writeln(c);
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
