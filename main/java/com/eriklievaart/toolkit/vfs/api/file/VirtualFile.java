package com.eriklievaart.toolkit.vfs.api.file;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.vfs.api.VirtualFileComparator;

public interface VirtualFile {

	public VirtualFileContent getContent();

	public boolean createFile();

	public VirtualFileUrl getUrl();

	public boolean exists();

	public boolean isDirectory();

	public boolean isFile();

	public boolean isHidden();

	public VirtualFile resolve(String name);

	public Optional<? extends VirtualFile> getParentFile();

	public void copyTo(VirtualFile destination);

	public void moveTo(VirtualFile destination);

	public String getName();

	public String getPath();

	public String getBaseName();

	public String getExtension();

	public Iterable<VirtualFile> scan(String... extensions);

	public String getRelativePathOf(VirtualFile child);

	public boolean mkdir();

	public long length();

	public List<? extends VirtualFile> getChildren();

	public default List<VirtualFile> getChildren(VirtualFileType type) {
		List<VirtualFile> result = NewCollection.list();

		for (VirtualFile child : getChildren()) {

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
	public default List<? extends VirtualFile> getChildrenAlphabeticallyDirectoriesFirst() {
		List<? extends VirtualFile> children = getChildren();
		Collections.sort(children, new VirtualFileComparator());
		return children;
	}

	/**
	 * Return the {@link VirtualFile} if it exists, otherwise return the longest existing parent path.
	 *
	 * @param child
	 *            VirtualFile we hope exists.
	 */
	public default VirtualFile getLongestExistingPath() {
		if (exists()) {
			return this;
		}
		Optional<? extends VirtualFile> optional = getParentFile();
		if (!optional.isPresent()) {
			throw new RuntimeIOException("Root of protocol % does not exist!", getUrl().getProtocol());
		}
		return optional.get().getLongestExistingPath();
	}

	/**
	 * Returns true iff this VirtualFile is a parent of child.
	 */
	public default boolean isParentOf(final VirtualFile child) {
		Check.notNull(child);

		if (getClass() != child.getClass()) {
			return false;
		}
		if (getPath().equals(child.getPath())) {
			return true;
		}
		String parentOfChild = UrlTool.getParent(child.getPath());
		return UrlTool.addSlash(parentOfChild).startsWith(UrlTool.addSlash(getPath()));
	}

	public void delete();

}
