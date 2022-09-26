package com.eriklievaart.toolkit.io.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class FileTool {
	private static final CopyOption[] NIO_OPTIONS = new CopyOption[2];

	static {
		NIO_OPTIONS[0] = StandardCopyOption.REPLACE_EXISTING;
		NIO_OPTIONS[1] = LinkOption.NOFOLLOW_LINKS;
	}

	public static FileInputStream toInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void writeStringToFile(String data, File file) {
		try {
			file.getParentFile().mkdirs();
			StreamTool.writeString(data, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static List<String> readLines(File file) {
		try {
			try (InputStream is = new FileInputStream(file)) {
				return StreamTool.readLines(is);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void writeLines(File file, Collection<String> lines) {
		try {
			file.getParentFile().mkdirs();
			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {

				for (String line : lines) {
					bw.write(line);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void copyFile(File from, OutputStream to) {
		try {
			StreamTool.copyStream(new FileInputStream(from), to);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}

	}

	public static void copyFile(File from, File to) {
		Check.notNull(from, to);
		errorOnChildOfItself(from, to);
		errorOnDirectoryToFile(from, to);
		try {
			boolean fileToDir = from.isFile() && to.isDirectory();
			File destination = fileToDir ? new File(to, from.getName()) : to;
			RuntimeIOException.on(destination.equals(from), "Cannot copy File to itself %", from);
			copyRecursive(from, destination);

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	private static void copyRecursive(File from, File to) throws IOException {
		File[] children = from.listFiles();
		if (children == null || children.length == 0) {
			if (from.isDirectory()) {
				to.mkdirs();
			} else {
				to.getParentFile().mkdirs();
				Files.copy(from.toPath(), to.toPath(), NIO_OPTIONS);
			}
		} else {
			for (File child : children) {
				copyFile(child, new File(to, child.getName()));
			}
		}
	}

	public static void moveFile(File from, File to) {
		Check.notNull(from, to);
		errorOnChildOfItself(from, to);
		errorOnDirectoryToFile(from, to);

		try {
			boolean fileToDir = from.isFile() && to.isDirectory();
			File destination = fileToDir ? new File(to, from.getName()) : to;
			destination.getParentFile().mkdirs();
			Files.move(from.toPath(), destination.toPath(), NIO_OPTIONS);

		} catch (DirectoryNotEmptyException e) {
			// weird special case in NIO, where a move is not allowed to happen across drives
			copyFile(from, to);
			delete(from);

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	private static void errorOnDirectoryToFile(File from, File to) {
		RuntimeIOException.on(from.isDirectory() && to.isFile(), "% -> % dir to file", to, from);
	}

	private static void errorOnChildOfItself(File from, File to) {
		boolean isChildOfItself = to.getAbsolutePath().startsWith(from.getAbsolutePath() + "/");
		RuntimeIOException.on(isChildOfItself, "% is a child of source dir %", to, from);
	}

	public static void delete(File head, File... tail) {
		delete(head);
		for (File next : tail) {
			delete(next);
		}
	}

	public static void delete(File delete) {
		if (delete == null) {
			return;
		}
		if (delete.isDirectory()) {
			for (File file : delete.listFiles()) {
				delete(file);
			}
		}
		delete.delete();
	}

	public static String toString(File file) {
		return String.join("\r\n", readLines(file));

	}

	public static void clean(File directory) {
		if (directory.exists()) {
			for (File child : directory.listFiles()) {
				delete(child);
			}
		}
	}
}