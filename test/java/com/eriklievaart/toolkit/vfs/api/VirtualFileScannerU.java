package com.eriklievaart.toolkit.vfs.api;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFileSystem;

public class VirtualFileScannerU {

	@Test
	public void dirFilter() throws IOException {
		MemoryFile parent = new MemoryFileSystem().resolve("parent");

		MemoryFile picture = parent.resolve("pictures/picture.jpg");
		picture.createFile();
		MemoryFile text = parent.resolve("books/text.txt");
		text.createFile();

		VirtualFileScanner scanner = new VirtualFileScanner(parent);
		scanner.addDirectoryFilter((file) -> file.getName().equals("pictures"));

		Assertions.assertThat(scanner.iterator()).containsExactly(picture);
	}

	@Test
	public void fileFilter() throws IOException {
		MemoryFile parent = new MemoryFileSystem().resolve("parent");

		MemoryFile picture = parent.resolve("pictures/picture.jpg");
		picture.createFile();
		MemoryFile text = parent.resolve("books/text.txt");
		text.createFile();

		VirtualFileScanner scanner = new VirtualFileScanner(parent);
		scanner.addFileFilter((file) -> file.getUrl().getExtension().equals("txt"));

		Assertions.assertThat(scanner.iterator()).containsExactly(text);
	}

	@Test
	public void restrictFileExtensionsTo() {
		MemoryFile parent = new MemoryFileSystem().resolve("parent");

		MemoryFile picture = parent.resolve("pictures/picture.jpg");
		picture.createFile();
		MemoryFile text = parent.resolve("books/text.txt");
		text.createFile();

		VirtualFileScanner scanner = new VirtualFileScanner(parent);
		scanner.restrictFileExtensionsTo("txt");

		Assertions.assertThat(scanner.iterator()).containsExactly(text);
	}
}
