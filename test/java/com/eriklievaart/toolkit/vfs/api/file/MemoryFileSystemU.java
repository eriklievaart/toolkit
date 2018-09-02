package com.eriklievaart.toolkit.vfs.api.file;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFileSystem;

public class MemoryFileSystemU {

	@Test
	public void resolveRoot() throws Exception {
		MemoryFileSystem resolver = new MemoryFileSystem();
		MemoryFile resolved = resolver.resolve("mem:/");
		CheckStr.isEqual(resolved.getPath(), "/");
	}

	@Test
	public void resolve() throws Exception {
		MemoryFileSystem resolver = new MemoryFileSystem();
		MemoryFile resolved = resolver.resolve("mem:/tmp");
		CheckStr.isEqual(resolved.getPath(), "/tmp");
	}

	@Test
	public void resolveSingleton() throws Exception {
		MemoryFileSystem resolver = new MemoryFileSystem();
		MemoryFile a = resolver.resolve("mem:/tmp/a");
		MemoryFile b = resolver.resolve("mem:/tmp/b");

		Check.isTrue(a == resolver.resolve("mem:/tmp/a"));
		Check.isTrue(a.getParentFile().get() == b.getParentFile().get());
	}

}
