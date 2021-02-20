package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

public class MyShell implements Environment {
	private Character promptSymbol;
	private Character multilineSymbol;
	private Character morelineSymbol;
	private SortedMap<String, ShellCommand> commands;
	private Scanner sc;

	public MyShell() {
		commands = new TreeMap<>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("exit", new ExitShellCommand());

		commands = Collections.unmodifiableSortedMap(commands);

		promptSymbol = '>';
		multilineSymbol = '|';
		morelineSymbol = '\\';

		sc = new Scanner(System.in);

		this.greeting();
	}

	@Override
	public String readLine() throws ShellIOException {
		write(getPromptSymbol().toString() + " ");
		String line = "";

		try {
			boolean first = true;

			do {
				if (first) {
					first = false;
				} else {
					line = line.substring(0, line.length() - 1);
					write(getMultilineSymbol().toString() + " ");
				}

				if (sc.hasNextLine()) {
					line += sc.nextLine();
				}
			} while (line.endsWith(getMorelinesSymbol().toString()));

		} catch (Exception e) {
			throw new ShellIOException(e);
		}

		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.format(text);
		} catch (Exception e) {
			throw new ShellIOException(e);
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException(e);
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelineSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelineSymbol = symbol;
	}

	private void greeting() {
		writeln("Welcome to MyShell v 1.0");
	}

	public void close() {
		this.sc.close();
	}

	public static void main(String[] args) {
		MyShell shell = new MyShell();

		ShellStatus status = ShellStatus.CONTINUE;
		do {
			String line = shell.readLine();
			String[] split = line.trim().split(" ", 2);
			String commandName = split[0];
			String arguments = new String("");
			if (split.length == 2) {
				arguments = split[1];
			}

			ShellCommand command = shell.commands().get(commandName);
			if (command == null) {
				System.err.println(
						"Command " + commandName + " does not exists. Use command help if you need some help!");
				continue;
			}

			try {
				status = command.executeCommand(shell, arguments);
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			} catch (ShellIOException e) {
				System.err.println("An error occured while reading/writing:");
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println("Unexpected error occured, MyShell might not work properly!");
				System.err.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		} while (status == ShellStatus.CONTINUE);

		shell.close();
	}
}
