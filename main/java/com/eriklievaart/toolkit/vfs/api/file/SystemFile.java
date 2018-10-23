package com.eriklievaart.toolkit.vfs.api.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class SystemFile extends AbstractVirtualFile {

	private File file;
	private boolean link;

	public SystemFile(File file) {
		Check.notNull(file);
		try {
			this.file = file.getCanonicalFile();
			this.link = Files.isSymbolicLink(file.toPath());

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public SystemFile(String path) {
		this.file = new File(path);
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public long length() {
		return file.length();
	}

	@Override
	public boolean isDirectory() {
		return file.isDirectory();
	}

	@Override
	public boolean isFile() {
		return file.isFile();
	}

	@Override
	public boolean isHidden() {
		return file.isHidden();
	}

	@Override
	public List<VirtualFile> getChildren() {
		onlyForDirectories();
		if (link) {
			return NewCollection.list();
		}
		File[] files = file.listFiles();
		RuntimeIOException.on(files == null, "Access denied!");
		return Arrays.stream(files).map(SystemFile::new).collect(Collectors.toList());
	}

	@Override
	public SystemFile resolve(String name) {
		if (name.startsWith("/")) {
			return new SystemFile(new File(name));
		}
		return new SystemFile(new File(file, name));
	}

	@Override
	public String getPath() {
		return file.getAbsolutePath();
	}

	@Override
	public boolean mkdir() {
		return file.mkdirs();
	}

	@Override
	public Optional<SystemFile> getParentFile() {
		File parent = file.getParentFile();
		return parent == null ? Optional.empty() : Optional.of(new SystemFile(parent));
	}

	public File unwrap() {
		return file;
	}

	@Override
	public void copyTo(VirtualFile destination) {
		if (destination instanceof SystemFile) {
			SystemFile dsf = (SystemFile) destination;
			FileTool.copyFile(file, dsf.file);
		} else {
			super.copyTo(destination);
		}
	}

	@Override
	public void moveTo(VirtualFile destination) {
		if (destination instanceof SystemFile) {
			SystemFile dsf = (SystemFile) destination;
			FileTool.moveFile(file, dsf.file);
		} else {
			super.moveTo(destination);
		}
	}

	@Override
	public boolean createFile() {
		try {
			file.getParentFile().mkdirs();
			return file.createNewFile();
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	@Override
	public void delete() {
		FileTool.delete(file);
	}

	@Override
	public InputStream getInputStream() {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	@Override
	public OutputStream getOutputStream() {
		try {
			file.getParentFile().mkdirs();
			return new FileOutputStream(file);

		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	@Override
	public String getProtocol() {
		return "file";
	}

}
