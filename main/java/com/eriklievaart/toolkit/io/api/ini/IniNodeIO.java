package com.eriklievaart.toolkit.io.api.ini;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.StreamTool;

public class IniNodeIO {

	public static void write(List<IniNode> nodes, File file) {
		IniNodeStringBuilder builder = new IniNodeStringBuilder();
		for (IniNode node : nodes) {
			builder.append(node);
		}
		FileTool.writeStringToFile(builder.toString(), file);
	}

	public static List<IniNode> read(File file) {
		List<String> lines = FileTool.readLines(file);
		return new IniNodeParser().parse(lines);
	}

	public static List<IniNode> read(InputStream is) {
		List<String> lines = StreamTool.readLines(is);
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
