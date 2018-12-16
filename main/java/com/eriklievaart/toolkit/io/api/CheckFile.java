package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CheckFile {

	public static void isFile(File file) {
		RuntimeIOException.on(file == null || !file.isFile(), "Not a file! $", file);
	}

	public static void isFile(File file, String msg) {
		RuntimeIOException.on(file == null || !file.isFile(), msg);
	}

	public static void isFile(File file, String msg, Object... args) {
		RuntimeIOException.on(file == null || !file.isFile(), msg, args);
	}

	public static void isDirectory(File file) {
		RuntimeIOException.on(file == null || !file.isDirectory(), "Not a directory! $", file);
	}

	public static void isDirectory(File file, String msg) {
		RuntimeIOException.on(file == null || !file.isDirectory(), msg);
	}

	public static void isDirectory(File file, String msg, Object... args) {
		RuntimeIOException.on(file == null || !file.isDirectory(), msg, args);
	}

	public static void exists(File file) {
		RuntimeIOException.on(file == null || !file.exists(), "File does not exist! $", file);
	}

	public static void notExists(File file) {
		RuntimeIOException.on(file == null, "File is <null>");
		RuntimeIOException.on(file.exists(), "File does exists! $", file);
	}

	public static void containsData(File file, String data) {
		isFile(file);
		RuntimeIOException.unless(FileTool.toString(file).equals(data), "data was %", data);
	}

	public static void isEmptyDirectory(File file) {
		isDirectory(file);
		List<String> children = Arrays.asList(file.list());
		RuntimeIOException.unless(children.isEmpty(), "dirctory not empty: $", children);
	}

	public static void isDirectoryWithChildren(File file) {
		isDirectory(file);
		RuntimeIOException.on(file.list().length == 0, "No children! $", file);
	}
}