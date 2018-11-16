package com.eriklievaart.toolkit.vfs.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class VirtualFileScanner implements Iterable<VirtualFile> {

	private final List<VirtualFileFilter> filterDirectories = NewCollection.list();
	private final List<VirtualFileFilter> filterFiles = NewCollection.list();
	private final List<VirtualFile> roots;

	public VirtualFileScanner(VirtualFile... roots) {
		this.roots = ListTool.of(roots);
	}

	public VirtualFileScanner addDirectoryFilter(VirtualFileFilter filter) {
		filterDirectories.add(filter);
		return this;
	}

	public VirtualFileScanner addFileFilter(VirtualFileFilter filter) {
		filterFiles.add(filter);
		return this;
	}

	public VirtualFileScanner restrictFileExtensionsTo(String... valid) {
		Set<String> set = new HashSet<>();
		for (String ext : valid) {
			Check.notBlank(ext);
			set.add(ext.toUpperCase().trim());
		}
		filterFiles.add(file -> set.contains(file.getUrl().getExtension().toUpperCase()));
		return this;
	}

	@Override
	public Iterator<VirtualFile> iterator() {
		ArrayList<VirtualFile> rootClone = new ArrayList<>(roots);
		ArrayList<VirtualFileFilter> fileFilterClone = new ArrayList<>(filterFiles);
		ArrayList<VirtualFileFilter> dirFilterClone = new ArrayList<>(filterDirectories);

		return new VirtualFileIterator(rootClone, dirFilterClone, fileFilterClone);
	}

}
