package com.eriklievaart.toolkit.io.api.ini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;

public class IniNodeIO {

	public static void write(List<IniNode> nodes, File file) {
		IniNodeStringBuilder builder = new IniNodeStringBuilder();
		for (IniNode node : nodes) {
			builder.append(node);
		}
		FileTool.writeStringToFile(builder.toString(), file);
	}

	public static List<IniNode> read(File file) {
		try {
			return read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException("unable to read file: " + file, e);
		}
	}

	public static List<IniNode> read(InputStream is) {
		List<String> lines = new LineFilter(is).eof().dropHash().list();
		return new IniNodeParser().parse(lines);
	}

	public static String toString(List<IniNode> nodes) {
		IniNodeStringBuilder builder = new IniNodeStringBuilder();
		for (IniNode node : nodes) {
			builder.append(node);
		}
		return builder.toString();
	}
}