package com.eriklievaart.toolkit.vfs.api.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import com.eriklievaart.toolkit.io.api.StreamTool;

public interface VirtualFileContent {

	public InputStream getInputStream();

	public OutputStream getOutputStream();

	public default String readString() {
		return StreamTool.toString(getInputStream());
	}

	public default void writeString(String data) {
		StreamTool.writeString(data, getOutputStream());
	}

	public default List<String> readLines() {
		return StreamTool.readLines(getInputStream());
	}

	public default void writeLines(Collection<String> lines) {
		StreamTool.writeLines(lines, getOutputStream());
	}
}