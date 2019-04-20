package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.eriklievaart.toolkit.mock.BombSquad;

public class FileToolU {

	private File sandbox = new File("/tmp/sandbox");

	@Before
	public void setup() {
		sandbox.mkdirs();
		FileTool.clean(sandbox);
	}

	@Test
	public void deleteEmptyDirectory() {
		sandbox.mkdirs();
		CheckFile.isDirectory(sandbox);
		FileTool.delete(sandbox);
		CheckFile.notExists(sandbox);
	}

	@Test
	public void deleteDirectoryWithFile() throws FileNotFoundException {
		File file = new File("/tmp/sandbox/file");
		FileTool.writeStringToFile("blah", file);
		CheckFile.isFile(file);

		FileTool.delete(sandbox);
		CheckFile.notExists(sandbox);
	}

	@Test
	public void deleteDirectoryWithNestedFile() throws FileNotFoundException {
		File file = new File("/tmp/sandbox/nested/file");
		FileTool.writeStringToFile("blah", file);
		CheckFile.isFile(file);

		FileTool.delete(sandbox);
		CheckFile.notExists(sandbox);
	}

	@Test
	public void copyFileOverwrite() throws IOException {
		File from = new File("/tmp/sandbox/a");
		File to = new File("/tmp/sandbox/b");

		FileTool.writeStringToFile("huzza", from);
		CheckFile.isFile(from);
		CheckFile.notExists(to);

		FileTool.copyFile(from, to);
		CheckFile.containsData(to, "huzza");
	}

	@Test
	public void copyFileCreateDestinationDirectory() throws IOException {
		File from = new File("/tmp/sandbox/from/a");
		File to = new File("/tmp/sandbox/to/b");

		FileTool.writeStringToFile("blah", from);
		CheckFile.isFile(from);

		to.getParentFile().mkdirs();
		CheckFile.notExists(to);
		CheckFile.isDirectory(to.getParentFile());

		FileTool.copyFile(from, to);
		CheckFile.containsData(to, "blah");
	}

	@Test
	public void copyFileWithNestedDirectoryAndFile() throws IOException {
		File from = new File("/tmp/sandbox/from");
		File to = new File("/tmp/sandbox/to");
		File soureFile = new File(from, "/nested/a");
		File createFile = new File(to, "/nested/a");

		FileTool.writeStringToFile("bar", soureFile);
		CheckFile.isFile(soureFile);

		CheckFile.notExists(to);
		CheckFile.notExists(createFile);

		FileTool.copyFile(from, to);
		CheckFile.containsData(createFile, "bar");
	}

	@Test
	public void copySingleFileToDirectory() throws Exception {
		File from = new File("/tmp/sandbox/from/a");
		File to = new File("/tmp/sandbox/to");
		to.mkdirs();

		FileTool.writeStringToFile("foo", from);
		CheckFile.isFile(from);
		CheckFile.isDirectory(to);

		FileTool.copyFile(from, to);
		CheckFile.containsData(new File("/tmp/sandbox/to/a"), "foo");
	}

	@Test
	public void copyDirectoryToFileFail() throws Exception {
		File from = new File("/tmp/sandbox/from");
		File to = new File("/tmp/sandbox/to");

		from.mkdirs();
		FileTool.writeStringToFile("error", to);
		CheckFile.isDirectory(from);
		CheckFile.containsData(to, "error");

		BombSquad.diffuse(RuntimeIOException.class, "dir to file", () -> {
			FileTool.copyFile(from, to);
		});
		CheckFile.isDirectory(from);
		CheckFile.containsData(to, "error");
	}

	@Test
	public void copyFileToItselfFail() throws Exception {
		File from = new File("/tmp/sandbox/from");

		FileTool.writeStringToFile("ignore", from);
		CheckFile.isFile(from);

		BombSquad.diffuse(RuntimeIOException.class, "itself", () -> {
			FileTool.copyFile(from, from);
		});
		CheckFile.containsData(from, "ignore");
	}

	@Test
	public void copyDirectoryToItsOwnChildFail() throws Exception {
		File from = new File("/tmp/sandbox/from");
		File to = new File("/tmp/sandbox/from/child");
		to.mkdirs();

		BombSquad.diffuse(RuntimeIOException.class, "is a child of", () -> {
			FileTool.copyFile(from, to);
		});
	}

	@Test
	public void moveSingleFileToFile() throws Exception {
		File from = new File("/tmp/sandbox/a");
		File to = new File("/tmp/sandbox/b");

		FileTool.writeStringToFile("huzza", from);
		CheckFile.isFile(from);
		CheckFile.notExists(to);

		FileTool.moveFile(from, to);
		CheckFile.containsData(to, "huzza");
		CheckFile.notExists(from);
	}

	@Test
	public void moveSingleFileToDirectory() throws Exception {
		File from = new File("/tmp/sandbox/from/a");
		File to = new File("/tmp/sandbox/to");
		to.mkdirs();

		FileTool.writeStringToFile("bar", from);
		CheckFile.isFile(from);
		CheckFile.isDirectory(to);

		FileTool.moveFile(from, to);
		CheckFile.containsData(new File("/tmp/sandbox/to/a"), "bar");
		CheckFile.notExists(from);
	}

	@Test
	public void moveDirectoryWithChild() throws Exception {
		File from = new File("/tmp/sandbox/from");
		File to = new File("/tmp/sandbox/to");
		File soureFile = new File(from, "/nested/a");
		File createFile = new File(to, "/nested/a");

		FileTool.writeStringToFile("bar", soureFile);
		CheckFile.isFile(soureFile);
		CheckFile.notExists(to);
		CheckFile.notExists(createFile);

		FileTool.moveFile(from, to);
		CheckFile.containsData(createFile, "bar");
		CheckFile.notExists(from);
	}

	@Test
	public void moveFileToItselfIgnore() throws Exception {
		File from = new File("/tmp/sandbox/from");

		FileTool.writeStringToFile("ignore", from);
		CheckFile.isFile(from);

		FileTool.moveFile(from, from);
		CheckFile.containsData(from, "ignore");
	}

	@Test
	public void moveDirectoryToFileFail() throws Exception {
		File from = new File("/tmp/sandbox/from");
		File to = new File("/tmp/sandbox/to");

		from.mkdirs();
		FileTool.writeStringToFile("error", to);
		CheckFile.isFile(to);

		BombSquad.diffuse(RuntimeIOException.class, "dir to file", () -> {
			FileTool.moveFile(from, to);
		});
		CheckFile.isDirectory(from);
		CheckFile.containsData(to, "error");
	}

	@Test
	public void moveDirectoryToItsOwnChildFail() throws Exception {
		File from = new File("/tmp/sandbox/from");
		File to = new File("/tmp/sandbox/from/child");
		to.mkdirs();

		BombSquad.diffuse(RuntimeIOException.class, "is a child of", () -> {
			FileTool.moveFile(from, to);
		});
	}
}
