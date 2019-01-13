package com.eriklievaart.toolkit.vfs.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.collection.FilteredNotNullList;
import com.eriklievaart.toolkit.lang.api.collection.Heap;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class VirtualFileIterator implements Iterator<VirtualFile> {

	private VirtualFile next;
	private final Heap<VirtualFile> stack;
	private final List<VirtualFileFilter> filterDirs;
	private final List<VirtualFileFilter> filterFiles;

	public VirtualFileIterator(List<VirtualFile> roots, List<VirtualFileFilter> dirs, List<VirtualFileFilter> files) {
		this.stack = new FilteredNotNullList<>();
		this.filterDirs = new ArrayList<>(dirs);
		this.filterFiles = new ArrayList<>(files);

		for (VirtualFile root : roots) {
			stack.addAll(root.getChildren());
		}
		scan();
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public VirtualFile next() {
		VirtualFile current = next;
		next = scan();
		return current;
	}

	private VirtualFile scan() {
		while (!stack.isEmpty()) {
			VirtualFile file = stack.pop();

			if (file.isDirectory() && passesFilters(file, filterDirs)) {
				for (VirtualFile child : file.getChildrenAdvanced().noError().get()) {
					stack.push(child);
				}

			} else if (!file.isDirectory() && passesFilters(file, filterFiles)) {
				next = file;
				return file;
			}
		}
		return null;
	}

	private boolean passesFilters(VirtualFile file, List<VirtualFileFilter> filters) {
		for (VirtualFileFilter filter : filters) {
			if (!filter.accept(file)) {
				return false;
			}
		}
		return true;
	}
}
