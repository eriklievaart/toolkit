package com.eriklievaart.toolkit.vfs.api.pack;

import org.junit.Test;

import com.eriklievaart.toolkit.mock.BombSquad;
import com.eriklievaart.toolkit.vfs.api.check.VirtualFileCheck;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFileSystem;

public class VirtualFileCheckU {

	@Test
	public void isFilePass() {
		MemoryFileSystem memory = new MemoryFileSystem();
		MemoryFile file = memory.resolve("file");
		file.getContent().writeString("this is a file");
		VirtualFileCheck.isFile(file);
	}

	@Test
	public void isFileFail() {
		MemoryFileSystem memory = new MemoryFileSystem();
		memory.resolve("dir/file");
		BombSquad.diffuse("not a file", () -> VirtualFileCheck.isFile(memory.resolve("dir")));
	}

	@Test
	public void isDirectoryFail() {
		MemoryFileSystem memory = new MemoryFileSystem();
		MemoryFile file = memory.resolve("file");
		file.getContent().writeString("this is a file");
		BombSquad.diffuse("not a directory", () -> VirtualFileCheck.isDirectory(file));
	}

	@Test
	public void isDirectoryPass() {
		MemoryFileSystem memory = new MemoryFileSystem();
		memory.resolve("dir/file");
		VirtualFileCheck.isDirectory(memory.resolve("dir"));
	}

	@Test
	public void existsPass() {
		MemoryFileSystem memory = new MemoryFileSystem();
		MemoryFile file = memory.resolve("file");
		file.getContent().writeString("this is a file");
		VirtualFileCheck.exists(file);
	}

	@Test
	public void existsFail() {
		MemoryFileSystem memory = new MemoryFileSystem();
		MemoryFile file = memory.resolve("file");
		BombSquad.diffuse("does not exist", () -> VirtualFileCheck.exists(file));
	}
}
