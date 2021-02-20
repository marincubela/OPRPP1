package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.utils.Util;

public class TreeShellCommand implements ShellCommand {
	private final String name = "tree";
	
	private final List<String> description;

	public TreeShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to list a tree from a given directory.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> split = Util.parseArguments(arguments);
		if (split.size() != 1) {
			throw new IllegalArgumentException(
					"tree command has one argument, but was given " + split.size() + " arguments!");
		}

		Path start = Paths.get(split.get(0));
		if (!Files.isDirectory(start)) {
			throw new IllegalArgumentException("Given path name was not a directory!");
		}

		try {
			Files.walkFileTree(start, new TreeVisitor());
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

	private class TreeVisitor extends SimpleFileVisitor<Path> {
		private int razina = 0;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			razina++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			razina--;
			return FileVisitResult.CONTINUE;
		}

		private void print(Path file) {
			String res = new String("");

			for (int i = 0; i < razina; i++) {
				res += "  ";
			}

			res += file.getFileName();
			System.out.println(res);
		}

	}
}
