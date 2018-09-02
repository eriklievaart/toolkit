package com.eriklievaart.toolkit.vfs.api.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.FromCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class MemoryFile extends AbstractVirtualFile {
	private static final String FS_ROOT = "/";

	private MemoryFileSystem resolver;
	final List<MemoryFile> children = NewCollection.list();
	private byte[] contents;
	private String path;
	private FileType type;

	public MemoryFile(String path, MemoryFileSystem resolver) {
		Check.notNull(path, resolver);
		this.path = path;
		this.resolver = resolver;
		this.type = path.equals(FS_ROOT) ? FileType.DIRECTORY : FileType.UNKNOWN;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(contents);
	}

	@Override
	public OutputStream getOutputStream() {
		return new ByteArrayOutputStream() {
			@Override
			public void close() throws IOException {
				contents = toByteArray();
			}
		};
	}

	@Override
	public String readString() {
		onlyForFiles();
		return super.readString();
	}

	@Override
	public void writeString(String data) {
		onlyForFiles();
		createFile();
		super.writeString(data);
	}

	@Override
	public boolean createFile() {
		Check.isTrue(type == FileType.UNKNOWN || type == FileType.FILE, "Already a directory $", path);
		type = FileType.FILE;
		return true;
	}

	@Override
	public List<String> readLines() {
		onlyForFiles();
		return Arrays.asList(Str.splitLines(readString()));
	}

	@Override
	public void writeLines(Collection<String> lines) {
		onlyForFiles();
		writeString(Str.joinLines(lines));
	}

	@Override
	public boolean isDirectory() {
		return type == FileType.UNKNOWN || type == FileType.DIRECTORY;
	}

	@Override
	public boolean isFile() {
		return type == FileType.UNKNOWN || type == FileType.FILE;
	}

	@Override
	public boolean exists() {
		return type != FileType.UNKNOWN;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public List<MemoryFile> getChildren() {
		onlyForDirectories();
		if (path.equals(FS_ROOT)) {
			return resolver.getRoots();
		}
		return FromCollection.toList(children);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public MemoryFile resolve(String relative) {
		onlyForDirectories();
		return resolver.resolve(UrlTool.append(path, relative));
	}

	MemoryFile getOrCreateChild(String name) {
		onlyForDirectories();
		for (VirtualFile child : children) {
			if (child.getName().equals(name)) {
				return (MemoryFile) child;
			}
		}
		MemoryFile child = new MemoryFile(UrlTool.append(getPath(), name), resolver);
		children.add(child);
		type = FileType.DIRECTORY;
		return child;
	}

	@Override
	public boolean mkdir() {
		onlyForDirectories();
		type = FileType.DIRECTORY;
		return true; // noop
	}

	@Override
	public Optional<MemoryFile> getParentFile() {
		MemoryFile resolve = resolver.resolve(UrlTool.removeSlash(UrlTool.getParent(path)));
		return resolve == null ? Optional.empty() : Optional.of(resolve);
	}

	@Override
	public void delete() {
		resolver.delete(this);
	}

	@Override
	public String getProtocol() {
		return "mem";
	}

	@Override
	public long length() {
		return contents.length;
	}

	private enum FileType {
		UNKNOWN, FILE, DIRECTORY;
	}

}
