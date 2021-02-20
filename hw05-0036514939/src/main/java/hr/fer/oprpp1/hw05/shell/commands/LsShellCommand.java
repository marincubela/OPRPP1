package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.utils.Util;

public class LsShellCommand implements ShellCommand {
	private final String name = "ls";
	
	private final List<String> description;

	public LsShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to list all files and directories in a given directory.");
		list.add("Command must get directory as a second argument.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> split = Util.parseArguments(arguments);

		if (split.size() != 1) {
			throw new IllegalArgumentException(
					"ls command has one argument, but was given " + split.size() + " arguments!");
		}

		Path directory = Paths.get(split.get(0));

		if (!Files.isDirectory(directory)) {
			throw new IllegalArgumentException("Given path name was not a directory!");
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path child : stream) {
				env.writeln(getString(child));
			}

		} catch (IOException e) {
			throw new ShellIOException(e);
		}

		return ShellStatus.CONTINUE;
	}

	private String getString(Path child) {
		String line = new String("");
		
		try {
		BasicFileAttributeView faView = Files.getFileAttributeView(child, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attr;
			attr = faView.readAttributes();
			line += attr.isDirectory() ? 'd' : '-';
			line += Files.isReadable(child) ? 'r' : '-';
			line += Files.isWritable(child) ? 'w' : '-';
			line += Files.isExecutable(child) ? 'x' : '-';
			line += " ";

			// TODO calculate for directories
			line += String.format("%10d", Files.size(child));
			line += " ";
			
			FileTime fileTime = attr.creationTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			line += sdf.format(new Date(fileTime.toMillis()));
			line += " ";
			
			line += child.getFileName();
			
		} catch (IOException e) {
			throw new ShellIOException(e);
		}
		
		return line;
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
