package com.eriklievaart.toolkit.vfs.api.file;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.test.api.BombSquad;
import com.eriklievaart.toolkit.test.api.MemoryFileSystemCheck;

public class MemoryFileU {

	@Test
	public void getUrl() {
		VirtualFileUrl url = new MemoryFileSystem().resolve("/bla\\file with space.txt").getUrl();

		CheckStr.isEqual(url.getUrlEscaped(), "mem:///bla/file%20with%20space.txt");
		CheckStr.isEqual(url.getProtocol(), "mem");
		CheckStr.isEqual(url.getPathEscaped(), "/bla/file%20with%20space.txt");
		CheckStr.isEqual(url.getBaseNameEscaped(), "file%20with%20space");
		CheckStr.isEqual(url.getExtension(), "txt");

		Check.isEqual(url, new VirtualFileUrl("mem:///bla/file with space.txt"));
	}

	@Test
	public void resolveSimple() {
		MemoryFile parent = new MemoryFileSystem().resolve("parent");
		MemoryFile resolved = parent.resolve("file.txt");

		Check.isEqual(resolved.getName(), "file.txt");
		Assertions.assertThat(parent.getChildren()).containsExactly(resolved);
	}

	@Test
	public void resolveNormalize() {
		MemoryFile parent = new MemoryFileSystem().resolve("keep/remove/./..");
		Check.isEqual(parent.getPath(), "/keep");
	}

	@Test
	public void resolveNested() {
		MemoryFile root = new MemoryFileSystem().resolve("parent");

		MemoryFile parent = root.resolve("pictures");
		MemoryFile picture = root.resolve("pictures/picture.jpg");

		Check.isEqual(picture.getName(), "picture.jpg");
		Check.isEqual(picture.getPath(), "/parent/pictures/picture.jpg");
		Assertions.assertThat(root.getChildren()).containsExactly(parent);
		Assertions.assertThat(root.getChildren().iterator().next().getChildren()).containsExactly(picture);
	}

	@Test
	public void resolveChildOfRoot() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile parent = fs.resolve("parent");
		parent.mkdir();

		MemoryFile root = fs.resolve("/");
		Check.isEqual(root.getChildren().size(), 1);
	}

	@Test
	public void getRelativePath() {
		MemoryFile root = new MemoryFileSystem().resolve("/path/");
		MemoryFile nested = root.resolve("to/something");

		Check.isEqual(root.getRelativePathOf(nested), "to/something");
	}

	@Test
	public void setLastModified() {
		MemoryFileSystem fs = new MemoryFileSystem();

		MemoryFile file = fs.resolve("root/from");
		file.writeString("original");
		Check.isTrue(System.currentTimeMillis() - file.lastModified() < 100);

		file.setLastModified(200);
		Check.isEqual(file.lastModified(), 200l);
	}

	@Test
	public void copySingleFileToFile() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile fileA = fs.resolve("root/middle-a/file-a");
		MemoryFile fileB = fs.resolve("root/middle-b/file-b");

		fileA.writeString("AAA");
		fileB.writeString("BBB");

		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);
		check.isFile("root/middle-a/file-a", "AAA");
		check.isFile("root/middle-b/file-b", "BBB");

		fileA.copyTo(fileB);
		check.isFile("root/middle-a/file-a", "AAA");
		check.isFile("root/middle-b/file-b", "AAA");
	}

	@Test
	public void copySingleFileToDirectory() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile fileA = fs.resolve("root/middle-a/file-a");
		MemoryFile middleB = fs.resolve("root/middle-b");

		fileA.writeString("AAA");
		middleB.mkdir();

		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);
		check.isFile("root/middle-a/file-a", "AAA");
		check.notExists("root/middle-b/file-a");

		fileA.copyTo(middleB);
		check.isFile("root/middle-a/file-a", "AAA");
		check.isFile("root/middle-b/file-a", "AAA");
	}

	@Test
	public void copySingleFileRename() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile root = fs.resolve("root");
		MemoryFile fileA = root.resolve("file-a");
		MemoryFile fileB = root.resolve("file-b");

		fileA.writeString("AAA");

		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);
		check.isFile("root/file-a", "AAA");
		check.notExists("root/file-b");

		fileA.copyTo(fileB);
		check.isFile("root/file-a", "AAA");
		check.isFile("root/file-b", "AAA");
	}

	@Test
	public void copySingleDirectory() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		MemoryFile copy = fs.resolve("root/from");
		MemoryFile to = fs.resolve("root/to");
		copy.mkdir();
		to.mkdir();

		check.isDirectory("root/from");
		check.isDirectory("root/to");
		check.notExists("root/to/from");

		copy.copyTo(to);
		check.isDirectory("root/from");
		check.isDirectory("root/to");
		check.isDirectory("root/to/from");
	}

	@Test
	public void copySingleDirectoryWithChild() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		MemoryFile copy = fs.resolve("root/from");
		MemoryFile nested = fs.resolve("root/from/nested");
		MemoryFile to = fs.resolve("root/to");
		nested.writeString("nested");
		to.mkdir();

		check.isFile("/root/from/nested", "nested");
		check.isDirectory("root/to");
		check.notExists("root/to/from");

		copy.copyTo(to);
		check.isDirectory("root/from");
		check.isDirectory("root/to");
		check.isDirectory("root/to/from");
		check.isFile("/root/to/from/nested", "nested");
	}

	@Test
	public void copyDirectoryToFileFail() {
		MemoryFileSystem fs = new MemoryFileSystem();

		MemoryFile copy = fs.resolve("root/from");
		MemoryFile to = fs.resolve("root/to");
		copy.mkdir();
		to.createFile();

		BombSquad.diffuse(RuntimeIOException.class, "Not a directory", () -> {
			copy.copyTo(to);
		});
	}

	@Test
	public void copyFileToItselfFail() {
		MemoryFileSystem fs = new MemoryFileSystem();

		MemoryFile from = fs.resolve("root/from");
		from.writeString("original");

		BombSquad.diffuse(RuntimeIOException.class, "to itself", () -> {
			from.copyTo(from);
		});
	}

	@Test
	public void copyDirectoryToItsOwnChildFail() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		MemoryFile copy = fs.resolve("root/from");
		MemoryFile to = fs.resolve("root/from/to");
		to.mkdir();

		check.isDirectory("root/from/to");
		check.notExists("root/from/to/from");

		BombSquad.diffuse(RuntimeIOException.class, "child of itself", () -> {
			copy.copyTo(to);
		});

		check.isDirectory("root/from/to");
		check.notExists("root/from/to/from");
	}

	@Test
	public void moveSingleFileToFile() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile fileA = fs.resolve("root/middle-a/file-a");
		MemoryFile fileB = fs.resolve("root/middle-b/file-b");

		fileA.writeString("AAA");
		fileB.writeString("BBB");

		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);
		check.isFile("root/middle-a/file-a", "AAA");
		check.isFile("root/middle-b/file-b", "BBB");

		fileA.moveTo(fileB);
		check.notExists("root/middle-a/file-a");
		check.isFile("root/middle-b/file-b", "AAA");
	}

	@Test
	public void moveSingleFileToDirectory() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile fileA = fs.resolve("root/middle-a/file-a");
		MemoryFile middleB = fs.resolve("root/middle-b");

		fileA.writeString("AAA");
		middleB.mkdir();

		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);
		check.isFile("root/middle-a/file-a", "AAA");
		check.notExists("root/middle-b/file-a");

		fileA.moveTo(middleB);
		check.notExists("root/middle-a/file-a");
		check.isFile("root/middle-b/file-a", "AAA");
	}

	@Test
	public void moveSingleFileRename() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFile root = fs.resolve("root");
		MemoryFile fileA = root.resolve("file-a");
		MemoryFile fileB = root.resolve("file-b");

		fileA.writeString("AAA");

		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);
		check.isFile("root/file-a", "AAA");
		check.notExists("root/file-b");

		fileA.moveTo(fileB);
		check.notExists("root/file-a");
		check.isFile("root/file-b", "AAA");
	}

	@Test
	public void moveSingleDirectory() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		MemoryFile move = fs.resolve("root/from");
		MemoryFile to = fs.resolve("root/to");
		move.mkdir();
		to.mkdir();

		check.isDirectory("root/from");
		check.isDirectory("root/to");
		check.notExists("root/to/from");

		move.moveTo(to);
		check.notExists("root/from");
		check.isDirectory("root/to");
		check.isDirectory("root/to/from");
	}

	@Test
	public void moveSingleDirectoryWithChild() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		MemoryFile move = fs.resolve("root/from");
		MemoryFile nested = fs.resolve("root/from/nested");
		MemoryFile to = fs.resolve("root/to");
		nested.writeString("nested");
		to.mkdir();

		check.isFile("/root/from/nested", "nested");
		check.isDirectory("root/to");
		check.notExists("root/to/from");

		move.moveTo(to);
		check.notExists("root/from");
		check.isDirectory("root/to");
		check.isDirectory("root/to/from");
		check.isFile("/root/to/from/nested", "nested");
	}

	@Test
	public void moveDirectoryToFileFail() {
		MemoryFileSystem fs = new MemoryFileSystem();

		MemoryFile move = fs.resolve("root/from");
		MemoryFile to = fs.resolve("root/to");
		move.mkdir();
		to.createFile();

		BombSquad.diffuse(RuntimeIOException.class, "Not a directory", () -> {
			move.moveTo(to);
		});
	}

	@Test
	public void moveFileToItselfFail() {
		MemoryFileSystem fs = new MemoryFileSystem();

		MemoryFile from = fs.resolve("root/from");
		from.writeString("original");

		BombSquad.diffuse(RuntimeIOException.class, "to itself", () -> {
			from.moveTo(from);
		});
	}

	@Test
	public void moveDirectoryToItsOwnChildFail() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		MemoryFile move = fs.resolve("root/from");
		MemoryFile to = fs.resolve("root/from/to");
		to.mkdir();

		check.isDirectory("root/from/to");
		check.notExists("root/from/to/from");

		BombSquad.diffuse(RuntimeIOException.class, "child of itself", () -> {
			move.moveTo(to);
		});

		check.isDirectory("root/from/to");
		check.notExists("root/from/to/from");
	}

	@Test
	public void deleteSingleFile() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		fs.resolve("mem:/ram/a").writeString("bla");
		check.hasDirectChild("mem:/ram", "a");
		check.isFile("mem:/ram/a", "bla");

		fs.resolve("mem:/ram/a").delete();
		check.noDirectChild("mem:/ram", "a");
		check.notExists("mem:/ram/a");
	}

	@Test
	public void deleteSingleDirectory() {
		MemoryFileSystem fs = new MemoryFileSystem();
		MemoryFileSystemCheck check = new MemoryFileSystemCheck(fs);

		String parent = "mem:/ram/parent";
		String a = "mem:/ram/parent/a";
		String b = "mem:/ram/parent/b";

		fs.resolve(a).writeString("a");
		fs.resolve(b).writeString("b");

		check.isDirectory(parent);
		check.isFile(a, "a");
		check.isFile(b, "b");

		fs.resolve(parent).delete();
		check.notExists(parent);
		check.notExists(a);
		check.notExists(b);
	}
}
