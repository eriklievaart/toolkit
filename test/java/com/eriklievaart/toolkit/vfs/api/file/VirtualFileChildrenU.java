package com.eriklievaart.toolkit.vfs.api.file;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class VirtualFileChildrenU {

	@Test
	public void noError() {
		MemoryFileSystem memory = new MemoryFileSystem();
		VirtualFileChildrenError error = new VirtualFileChildrenError("dir", memory);
		List<? extends VirtualFile> children = error.getChildrenAdvanced().noError().get();
		CheckCollection.isEmpty(children);
	}

	@Test(expected = RuntimeIOException.class)
	public void error() {
		MemoryFileSystem memory = new MemoryFileSystem();
		VirtualFileChildrenError error = new VirtualFileChildrenError("dir", memory);
		error.getChildrenAdvanced().get();
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
		List<VirtualFile> result = root.getChildrenAdvanced().get(VirtualFileType.DIRECTORY);
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
		List<VirtualFile> result = root.getChildrenAdvanced().get(VirtualFileType.FILE);
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
		List<VirtualFile> result = root.getChildrenAdvanced().get(VirtualFileType.ALL);
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

		List<? extends VirtualFile> sorted = root.getChildrenAdvanced().getAlphabeticallyDirectoriesFirst();
		Stream<String> s = sorted.stream().map((file) -> file.getName());
		Assertions.assertThat(s).containsExactly("alpha", "beta", "revised", "unlimited", "ACRO", "Anime", "AUTORUN");
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
