package com.eriklievaart.toolkit.vfs.api.file;

import java.util.List;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;

public class VirtualFileChildrenError extends MemoryFile {

	public VirtualFileChildrenError(String path, MemoryFileSystem resolver) {
		super(path, resolver);
	}

	@Override
	public List<MemoryFile> getChildren() {
		throw new RuntimeIOException("VirtualFileChildrenError");
	}
}
