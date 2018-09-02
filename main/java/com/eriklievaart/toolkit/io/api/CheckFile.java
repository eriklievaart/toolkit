package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.util.Arrays;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class CheckFile {

	public static void isFile(File file) {
		Check.notNull(file);
		Check.isTrue(file.isFile(), "Not a file! $", file);
	}

	public static void isFile(File file, String msg) {
		Check.notNull(file, msg);
		Check.isTrue(file.isFile(), msg);
	}

	public static void isFile(File file, String msg, Object... args) {
		Check.notNull(file, "Argument is <null> so is not a file");
		Check.isTrue(file.isFile(), msg, args);
	}

	public static void isDirectory(File file) {
		Check.notNull(file);
		Check.isTrue(file.isDirectory(), "Not a directory! $", file);
	}

	public static void isDirectory(File file, String msg) {
		Check.notNull(file, msg);
		Check.isTrue(file.isDirectory(), msg);
	}

	public static void isDirectory(File file, String msg, Object... args) {
		Check.notNull(file, "Argument is <null> so is not a directory");
		Check.isTrue(file.isDirectory(), msg, args);
	}

	public static void exists(File file) {
		Check.notNull(file);
		Check.isTrue(file.exists(), "File does not exist! $", file);
	}

	public static void notExists(File file) {
		Check.notNull(file);
		Check.isFalse(file.exists(), "File exists! $", file);
	}

	public static void containsData(File file, String data) {
		isFile(file);
		Check.isEqual(FileTool.toString(file), data);
	}

	public static void isEmptyDirectory(File file) {
		isDirectory(file);
		CheckCollection.isEmpty(Arrays.asList(file.list()));
	}

	public static void isDirectoryWithChildren(File file) {
		isDirectory(file);
		Check.isFalse(file.list().length == 0, "No children! %", file);
	}
}