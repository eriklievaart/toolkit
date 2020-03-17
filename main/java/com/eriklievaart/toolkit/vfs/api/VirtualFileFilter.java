package com.eriklievaart.toolkit.vfs.api;

import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public interface VirtualFileFilter {

	public boolean accept(VirtualFile file);
}