package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {
	private final String commandName = "help";
	private final List<String> description;

	public HelpShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to help user with shell commands.");
		list.add("If no command is given for help, it lists all possible commands.");
		list.add("If a command is given, then it lists description for that command");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			for(var e : env.commands().entrySet()) {
				env.writeln(e.getKey());
			}
		} else {
			ShellCommand com = env.commands().get(arguments);
			env.writeln(com.getCommandName());
			
			String text = "";
			
			for(String s : com.getCommandDescription()) {
				text += s + "\n";
			}
			env.write(text);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
