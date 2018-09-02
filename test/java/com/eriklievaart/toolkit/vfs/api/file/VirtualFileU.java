package com.eriklievaart.toolkit.vfs.api.file;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class VirtualFileU {

	@Test
	public void scan() throws IOException {
		MemoryFile parent = new MemoryFileSystem().resolve("parent");

		MemoryFile picture = parent.resolve("picture.jpg");
		picture.createFile();
		MemoryFile text = parent.resolve("text.txt");
		text.createFile();

		Iterable<VirtualFile> textFiles = parent.scan("txt");
		Assertions.assertThat(textFiles.iterator()).containsExactly(text);

		Iterable<VirtualFile> pictureFiles = parent.scan("jpg");
		Assertions.assertThat(pictureFiles.iterator()).containsExactly(picture);
	}

	@Test
	public void getChildrenDirectories() {
		MemoryFile root = new MemoryFileSystem().resolve("/tmp/sandbox/test");
		VirtualFile file = root.resolve("file");
		file.getContent().writeString("foo");
		VirtualFile directory = root.resolve("directory");
		directory.resolve("child");

		Check.isTrue(file.isFile());
		Check.isTrue(directory.isDirectory());
		List<VirtualFile> result = root.getChildren(VirtualFileType.DIRECTORY);
		Assertions.assertThat(result).hasSize(1);
		Assertions.assertThat(result.get(0).getName()).isEqualTo("directory");
	}

	@Test
	public void getChildrenFiles() {
		MemoryFile root = new MemoryFileSystem().resolve("/tmp/sandbox/test");
		VirtualFile file = root.resolve("file");
		file.getContent().writeString("foo");
		VirtualFile directory = root.resolve("directory");
		directory.resolve("child");

		Check.isTrue(file.isFile());
		Check.isTrue(directory.isDirectory());
		List<VirtualFile> result = root.getChildren(VirtualFileType.FILE);
		Assertions.assertThat(result).hasSize(1);
		Assertions.assertThat(result.get(0).getName()).isEqualTo("file");
	}

	@Test
	public void getChildrenAll() {
		MemoryFile root = new MemoryFileSystem().resolve("/tmp/sandbox/test");
		VirtualFile file = root.resolve("file");
		file.getContent().writeString("foo");
		VirtualFile directory = root.resolve("directory");
		directory.resolve("child");

		Check.isTrue(file.isFile());
		Check.isTrue(directory.isDirectory());
		List<VirtualFile> result = root.getChildren(VirtualFileType.ALL);
		Assertions.assertThat(result).hasSize(2);
	}

	@Test
	public void getChildrenAlphabeticallyDirectoriesFirst() {
		MemoryFile root = new MemoryFileSystem().resolve("/tmp/sandbox/test");
		addFile(root, "AUTORUN");
		addFile(root, "Anime");
		addFile(root, "ACRO");
		addDirectory(root, "revised");
		addDirectory(root, "unlimited");
		addDirectory(root, "beta");
		addDirectory(root, "alpha");

		List<? extends VirtualFile> sorted = root.getChildrenAlphabeticallyDirectoriesFirst();
		Stream<String> s = sorted.stream().map((file) -> file.getName());
		Assertions.assertThat(s).containsExactly("alpha", "beta", "revised", "unlimited", "ACRO", "Anime", "AUTORUN");
	}

	@Test
	public void getLongestExistingParentInvalid() {
		VirtualFile file = new MemoryFileSystem().resolve("i-do-not-exist");
		Check.isEqual(file.getLongestExistingPath().getPath(), "/");
	}

	@Test
	public void isParentOfDifferent() {
		Check.isFalse(new MemoryFileSystem().resolve("a").isParentOf(new MemoryFileSystem().resolve("b")));
	}

	@Test
	public void isParentOfSamePrefixButDifferent() {
		Check.isFalse(new MemoryFileSystem().resolve("sameprefix-123")
				.isParentOf(new MemoryFileSystem().resolve("sameprefix")));
	}

	@Test
	public void isParentOfSamePrefixButDifferent2() {
		VirtualFile test = new MemoryFileSystem().resolve("dir");
		VirtualFile unrelated = new MemoryFileSystem().resolve("dir2/nested");

		Check.isFalse(test.isParentOf(unrelated));
	}

	@Test
	public void isParentOfItself() {
		VirtualFile same = new MemoryFileSystem().resolve("same");
		Check.isTrue(same.isParentOf(same));
	}

	@Test
	public void isParentOfChild() {
		VirtualFile parent = new MemoryFileSystem().resolve("parent");
		VirtualFile child = new MemoryFileSystem().resolve("parent/child");

		Check.isTrue(parent.isParentOf(child));
	}

	@Test
	public void isParentOfParent() {
		VirtualFile parent = new MemoryFileSystem().resolve("parent");
		VirtualFile child = new MemoryFileSystem().resolve("parent/child");

		Check.isFalse(child.isParentOf(parent));
	}

	private VirtualFile addFile(MemoryFile parent, String path) {
		VirtualFile file = parent.resolve(path);
		file.getContent().writeString("foo");
		return file;
	}

	private VirtualFile addDirectory(MemoryFile parent, String path) {
		VirtualFile file = parent.resolve(path);
		file.resolve("_");
		return file;
	}
}
