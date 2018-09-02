package com.eriklievaart.toolkit.vfs.api;

import java.util.Comparator;

import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

/**
 * Comparator for comparing {@link VirtualFile}'s.
 * 
 * @author Erik Lievaart
 */
public class VirtualFileComparator implements Comparator<VirtualFile> {

	private final VirtualFileNameComparator delegate = new VirtualFileNameComparator();

	/**
	 * Compare two files alphabetically. Compares the names of the files. Sorts directories first.
	 */
	@Override
	public int compare(final VirtualFile o1, final VirtualFile o2) {
		if (o1.isDirectory() != o2.isDirectory()) {
			return o1.isDirectory() ? -100 : 100;
		}
		return delegate.compare(o1.getName(), o2.getName());
	}

}
