package com.eriklievaart.toolkit.mock;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFileSystem;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class MemoryFileSystemCheck {

	private MemoryFileSystem fs;

	public MemoryFileSystemCheck(MemoryFileSystem fs) {
		this.fs = fs;
	}

	public void notFile(String path) {
		Check.notNull(path);
		Check.isFalse(fs.resolve(path).isFile(), "Not a file: $", path);
	}

	public void isFile(String path, String data) {
		Check.notNull(path);
		MemoryFile file = fs.resolve(path);
		Check.isTrue(file.isFile(), "Not a file: $", path);
		CheckStr.isEqual(file.readString(), data);
	}

	public void isDirectory(String path) {
		Check.notNull(path);
		MemoryFile file = fs.resolve(path);
		Check.isFalse(file.isFile(), "% is a File", path);
	}

	public void notExists(String path) {
		Check.notNull(path);
		MemoryFile file = fs.resolve(path);
		Check.isFalse(file.exists(), "% does not exist", path);
	}

	public void hasDirectChild(String path, String name) {
		MemoryFile parent = fs.resolve(path);
		for (VirtualFile child : parent.getChildren()) {
			if (child.getName().equals(name)) {
				return;
			}
		}
		throw new AssertionException("Child % not found in $", name, parent.getChildren());
	}

	public void noDirectChild(String path, String name) {
		MemoryFile parent = fs.resolve(path);
		for (VirtualFile child : parent.getChildren()) {
			if (child.getName().equals(name)) {
				throw new AssertionException("Child % found in $", name, parent.getChildren());
			}
		}
	}
}