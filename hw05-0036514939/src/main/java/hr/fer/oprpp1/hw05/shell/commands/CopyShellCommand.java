package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class CopyShellCommand implements ShellCommand {
	private final String name = "copy";
	private static final int BUFFER_SIZE = 4096;

	private final List<String> description;

	public CopyShellCommand() {
		List<String> list = new ArrayList<>();
		list.add("This command copies first file to location given by second file.");
		list.add("Source file can't be directory.");
		list.add(
				"If destination file s directory, source file will be copied into directory with name of source file.");

		description = Collections.unmodifiableList(list);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> split = Util.parseArguments(arguments);

		if (split.size() != 2) {
			throw new IllegalArgumentException(
					"copy command has two arguments, but was given " + split.size() + " arguments!");
		}

		Path source = Paths.get(split.get(0));
		Path dest = Paths.get(split.get(1));

		if (!Files.exists(source)) {
			throw new IllegalArgumentException("Source file does not exists. Please provide file that exists!");
		}

		if (Files.isDirectory(source)) {
			throw new IllegalArgumentException("copy command only works with files and not with directories!");
		}

		if (Files.isDirectory(dest)) {
			dest = dest.resolve(source.getFileName());
		}

		try (InputStream is = new BufferedInputStream(new FileInputStream(source.toString()));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(dest.toString()))) {
			if (!Files.exists(dest)) {
				Files.createDirectories(dest);
				Files.createFile(dest);
			}
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;

			while ((read = is.read(buffer)) == BUFFER_SIZE) {
				os.write(buffer);
			}

			byte[] finalBuffer = new byte[read];
			for (int i = 0; i < read; i++) {
				finalBuffer[i] = buffer[i];
			}

			os.write(finalBuffer);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("One of the files does not exists. Please provide files that exists!");
		} catch (IOException e) {
			throw new ShellIOException(e);
		}

		env.writeln("File " + split.get(0) + " successfully copied to " + split.get(1));
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
