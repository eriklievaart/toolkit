package com.eriklievaart.toolkit.vfs.api.file;

import java.io.IOException;

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
}
