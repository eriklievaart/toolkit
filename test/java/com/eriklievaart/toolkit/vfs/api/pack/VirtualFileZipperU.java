package com.eriklievaart.toolkit.vfs.api.pack;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.mock.SandboxTest;
import com.eriklievaart.toolkit.vfs.api.check.VirtualFileCheck;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;

public class VirtualFileZipperU extends SandboxTest {

	@Test
	public void zipFile() {
		MemoryFile source = memoryFile("source.txt");
		MemoryFile destination = memoryFile("destination.zip");
		source.getContent().writeString("body");

		checkNotExists("destination.zip");
		VirtualFileZipper.zip(source, destination);
		Assertions.assertThat(VirtualFileZipper.entries(destination)).containsExactly("source.txt");
	}

	@Test
	public void zipDir() {
		MemoryFile a = memoryFile("dir/a.txt");
		MemoryFile b = memoryFile("dir/b.txt");
		MemoryFile destination = memoryFile("destination.zip");

		a.getContent().writeString("aaaa");
		b.getContent().writeString("bbbb");
		checkNotExists("destination.zip");

		VirtualFileZipper.zip(memoryFile("dir"), destination);
		Assertions.assertThat(VirtualFileZipper.entries(destination)).containsExactly("a.txt", "b.txt");
	}

	@Test
	public void unzip() {
		MemoryFile a = memoryFile("dir/a.txt");
		MemoryFile b = memoryFile("dir/b.txt");
		MemoryFile zip = memoryFile("destination.zip");

		a.getContent().writeString("aaaa");
		b.getContent().writeString("bbbb");
		VirtualFileZipper.zip(memoryFile("dir"), zip);

		MemoryFile unzip = memoryFile("unzip");
		VirtualFileCheck.isEmpty(unzip);
		VirtualFileZipper.unzip(zip, unzip);
		Check.isEqual(unzip.resolve("a.txt").getContent().readString(), "aaaa");
		Check.isEqual(unzip.resolve("b.txt").getContent().readString(), "bbbb");
	}
}
