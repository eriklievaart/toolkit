package com.eriklievaart.toolkit.vfs.api.file;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.vfs.api.VirtualFileScanner;

public abstract class AbstractVirtualFile implements VirtualFile, VirtualFileContent {

	@Override
	public VirtualFileContent getContent() {
		return this;
	}

	@Override
	public VirtualFileUrl getUrl() {
		CheckStr.notBlank(getProtocol());
		String url = Str.sub("$://$", getProtocol(), getPath());
		return new VirtualFileUrl(url);
	}

	public abstract String getProtocol();

	@Override
	public String getName() {
		return UrlTool.getName(getPath());
	}

	public String getExtension() {
		return UrlTool.getExtension(getPath());
	}

	public String getBaseName() {
		return UrlTool.getBaseName(getPath());
	}

	protected void onlyForFiles() {
		if (!isFile()) {
			throw new RuntimeIOException("Not a regular File: $", getPath());
		}
	}

	protected void onlyForDirectories() {
		if (!isDirectory()) {
			throw new RuntimeIOException("Not a Directory: $", getPath());
		}
	}

	@Override
	public void copyTo(VirtualFile to) {
		if (getUrl().getProtocol().equals(to.getUrl().getProtocol())) {
			if (getPath().equals(to.getPath())) {
				throw new RuntimeIOException("Cannot copy a file to itself: $", getPath());
			}
			if (to.getPath().startsWith(getPath() + "/")) {
				throw new RuntimeIOException("Cannot copy to child of itself: $ => $", getPath(), to.getPath());
			}
		}
		if (isFile()) {
			copyFileTo(to);
		} else {
			copyDirectoryTo(to);
		}
	}

	@Override
	public void moveTo(VirtualFile destination) {
		copyTo(destination);
		delete();
	}

	private void copyDirectoryTo(VirtualFile to) {
		VirtualFile dirTo = to.resolve(getName());
		dirTo.mkdir();
		for (VirtualFile child : getChildren()) {
			child.copyTo(dirTo);
		}
	}

	private void copyFileTo(VirtualFile to) {
		boolean destinationIsFile = !to.exists() || to.isFile();
		VirtualFile destination = destinationIsFile ? to : to.resolve(getName());
		StreamTool.copyStream(getInputStream(), destination.getContent().getOutputStream());
	}

	@Override
	public String getRelativePathOf(VirtualFile child) {
		return UrlTool.getRelativePath(getPath(), child.getPath());
	}

	@Override
	public Iterable<VirtualFile> scan(String... extensions) {
		onlyForDirectories();
		return new VirtualFileScanner(this).restrictFileExtensionsTo(extensions);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VirtualFile) {
			VirtualFile other = (VirtualFile) obj;
			return getUrl().equals(other.getUrl());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getUrl().hashCode();
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$]", getPath());
	}
}
