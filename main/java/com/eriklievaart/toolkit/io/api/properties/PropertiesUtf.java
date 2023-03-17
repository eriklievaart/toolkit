package com.eriklievaart.toolkit.io.api.properties;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.StringEscape;

public class PropertiesUtf {

	public static void store(Map<String, String> props, File file) {
		Check.noneNull(props, file);
		FileTool.writeLines(file, getLines(props));
	}

	public static void store(Map<String, String> props, OutputStream os) {
		Check.noneNull(props, os);
		StreamTool.writeLines(getLines(props), os);
	}

	static List<String> getLines(Map<String, String> props) {
		return ListTool.map(props, (k, v) -> escape(k) + "=" + escape(v));
	}

	public static Map<String, String> load(File file) {
		CheckFile.exists(file);
		return parse(FileTool.readLines(file));
	}

	public static Map<String, String> load(InputStream is) {
		Check.notNull(is);
		return parse(StreamTool.readLines(is));
	}

	private static Map<String, String> parse(List<String> raw) {
		List<String> lines = new LineFilter(raw).dropBlank().dropHash().list();
		return MapTool.toMap(lines, l -> unescape(l.replaceFirst("=.*", "")), l -> unescape(l.replaceFirst(".*=", "")));
	}

	static String escape(String string) {
		return getEscapeMapping().escape(string);
	}

	static String unescape(String string) {
		return getEscapeMapping().unescape(string);
	}

	private static StringEscape getEscapeMapping() {
		Map<Character, Character> mapping = NewCollection.hashMap();
		mapping.put('b', '\\');
		mapping.put('e', '=');
		mapping.put('h', '#');
		return new StringEscape(mapping);
	}
}