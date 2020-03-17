package com.eriklievaart.toolkit.vfs.api.file;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.toolkit.vfs.api.VirtualFileComparator;

public class VirtualFileChildren {
	private LogTemplate log = new LogTemplate(getClass());

	private VirtualFile parent;
	private boolean error = true;

	public VirtualFileChildren(VirtualFile parent) {
		this.parent = parent;
	}

	public VirtualFileChildren noError() {
		error = false;
		return this;
	}

	public List<? extends VirtualFile> get() {
		try {
			return parent.getChildren();

		} catch (RuntimeIOException e) {
			if (error) {
				throw e;
			} else {
				log.debug("children not accessible: " + parent);
				return Collections.emptyList();
			}
		}
	}

	/**
	 * Get the children of the specified VirtualFileType.
	 */
	public List<VirtualFile> get(VirtualFileType type) {
		List<VirtualFile> result = NewCollection.list();

		for (VirtualFile child : get()) {

			boolean add = false;
			add = add || type == VirtualFileType.ALL;
			add = add || type == VirtualFileType.FILE && child.isFile();
			add = add || type == VirtualFileType.DIRECTORY && child.isDirectory();

			if (add) {
				result.add(child);
			}
		}
		return result;
	}

	/**
	 * Return the children of a {@link VirtualFile} in order.
	 *
	 * @param file
	 *            parent which' children to retrieve.
	 * @return the children in order.
	 * @see VirtualFileComparator
	 */
	public List<? extends VirtualFile> getAlphabeticallyDirectoriesFirst() {
		List<? extends VirtualFile> children = get();
		Collections.sort(children, new VirtualFileComparator());
		return children;
	}
}