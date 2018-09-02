package com.eriklievaart.toolkit.vfs.api.file;

import java.io.File;
import java.nio.file.FileSystemException;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.test.api.SandboxTest;

public class SystemFileU extends SandboxTest {

	@Before
	public void init() {
		createSandbox();
	}

	@After
	public void cleanup() {
		deleteSandboxFiles();
	}

	@Test
	public void resolveRelative() throws Exception {
		SystemFile foo = systemFile("foo");
		SystemFile bar = foo.resolve("bar");
		Check.isEqual(bar.getPath(), UrlTool.getPath(url("foo/bar")));
	}

	@Test
	public void resolveAbsolute() throws Exception {
		SystemFile foo = systemFile("foo");
		SystemFile bar = foo.resolve("/tmp");
		Check.isEqual(bar.getPath(), "/tmp");
	}

	@Test
	public void listFilesWithNumberSign() {
		String name = "file with number sign(#)";

		Check.isEqual(systemFile(name).getName(), name);
	}

	@Test
	public void getNamePercentile() {
		Check.isEqual(systemFile("%.txt").getName(), "%.txt");
	}

	@Test
	public void getNameEscapedPercentile() {
		Check.isEqual(systemFile("%.txt").getUrl().getNameEscaped(), "%25.txt");
	}

	@Test
	public void getBaseName() {
		String base = systemFile("test.001").getBaseName();
		Check.isEqual(base, "test");
	}

	@Test
	public void getNameEscapedComplex() {
		String name = "%5BEclipse%5D_Kimi_ni_Todoke_2nd_Season_-_00_(1280x720_h264)_%5B9AD4B336%5D.mkv.001";
		SystemFile file = systemFile(UrlTool.unescape(name));

		String base = file.getUrl().getNameEscaped();
		Assertions.assertThat(base).isEqualTo(name);
	}

	@Test
	public void getName() {
		Check.isEqual(systemFile("my file").getName(), "my file");
	}

	@Test
	public void getNameEscaped() {
		Check.isEqual(systemFile("/tmp/my file").getUrl().getNameEscaped(), "my%20file");
	}

	@Test
	public void getEscapedName() {
		Check.isEqual(systemFile("my%file").getName(), "my%file");
	}

	@Test
	public void getUrl() {
		Check.isEqual(systemFile("my file").getUrl().getUrlUnescaped(), url("my file"));
	}

	@Test
	public void getUrlEscaped() {
		Check.isEqual(systemFile("my file").getUrl().getUrlEscaped(), url("my%20file"));
	}

	@Test
	public void getEscapedUrlEscaped() {
		Check.isEqual(systemFile("my%file").getUrl().getUrlEscaped(), url("my%25file"));
	}

	@Test
	public void getRootUrl() throws Exception {
		SystemFile file = new SystemFile(new File("/"));
		String url = file.getUrl().getUrlEscaped();
		Check.isEqual(url, "file:///");
	}

	@Test
	public void getParentUrl() throws Exception {
		SystemFile file = new SystemFile(new File("/tmp/sandbox/skip/.."));
		String url = file.getUrl().getUrlEscaped();
		Check.isEqual(url, "file:///tmp/sandbox");
	}

	@Test
	public void getLongestExistingPathSelf() throws FileSystemException {
		File expected = file("self");
		expected.mkdirs();

		VirtualFile actual = new SystemFile(expected).getLongestExistingPath();
		Check.isEqual(actual.getPath(), expected.getAbsolutePath());
	}

	@Test
	public void getLongestExistingPath() {
		File expected = file("foo/bar");
		File nested = file("foo/bar/a/b/c/d");
		expected.mkdirs();

		VirtualFile actual = new SystemFile(nested).getLongestExistingPath();
		Check.isEqual(actual.getPath(), expected.getAbsolutePath());
	}

	@Test
	public void getParentFile() {
		SystemFile file = systemFile("a/b");
		Check.isEqual(file.getParentFile().get().getName(), "a");
	}

	@Test
	public void getParentFileRoot() {
		SystemFile file = new SystemFile(new File("/"));
		Check.isFalse(file.getParentFile().isPresent());
	}
	//*/

	@Test
	public void copyToMemory() {
		SystemFile testable = systemFile("a");
		MemoryFile destination = memoryFile("/ram/file");

		testable.writeString("boo!");
		testable.copyTo(destination);

		CheckFile.containsData(file("a"), "boo!");
		CheckStr.isEqual(testable.readString(), "boo!");
		CheckStr.isEqual(destination.readString(), "boo!");
	}

	@Test
	public void copyToFile() {
		File sourceFile = file("a");
		File destinationFile = file("b");
		SystemFile sourceSystemFile = new SystemFile(sourceFile);
		SystemFile destinationSystemFile = new SystemFile(destinationFile);

		sourceSystemFile.writeString("bleh!");
		sourceSystemFile.copyTo(destinationSystemFile);

		CheckFile.containsData(sourceFile, "bleh!");
		CheckFile.containsData(destinationFile, "bleh!");
		CheckStr.isEqual(sourceSystemFile.readString(), "bleh!");
		CheckStr.isEqual(destinationSystemFile.readString(), "bleh!");
	}

	@Test
	public void moveToMemory() {
		File file = file("a");
		SystemFile testable = new SystemFile(file);
		MemoryFile destination = memoryFile("/ram/file");

		testable.writeString("boo!");
		CheckFile.containsData(file, "boo!");
		CheckStr.isEqual(testable.readString(), "boo!");

		testable.moveTo(destination);

		CheckFile.notExists(file);
		Check.isFalse(testable.exists());
		CheckStr.isEqual(destination.readString(), "boo!");
	}

	@Test
	public void moveToFile() {
		File sourceFile = file("a");
		File destinationFile = file("b");
		SystemFile sourceSystemFile = new SystemFile(sourceFile);
		SystemFile destinationSystemFile = new SystemFile(destinationFile);

		sourceSystemFile.writeString("bleh!");
		sourceSystemFile.moveTo(destinationSystemFile);

		CheckFile.notExists(sourceFile);
		Check.isFalse(sourceSystemFile.exists());
		CheckFile.containsData(destinationFile, "bleh!");
		CheckStr.isEqual(destinationSystemFile.readString(), "bleh!");
	}
}
