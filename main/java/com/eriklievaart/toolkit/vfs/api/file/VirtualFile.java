package com.eriklievaart.toolkit.vfs.api.file;

import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.check.Check;

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

	public Iterable<VirtualFile> scan(String... extensions);

	public String getRelativePathOf(VirtualFile child);

	public boolean mkdir();

	public long length();

	public long lastModified();

	public void setLastModified(long stamp);

	public List<? extends VirtualFile> getChildren();

	public default VirtualFileChildren getChildrenAdvanced() {
		return new VirtualFileChildren(this);
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