package com.eriklievaart.toolkit.compare;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.collection.SetTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class DirComparator {

	private static final boolean FILE = true;
	private static final boolean DIRECTORY = false;

	private final File fromRoot;
	private final File toRoot;

	private final Map<String, String> changes = new TreeMap<>();
	private final Map<String, String> diff = new TreeMap<>();

	public DirComparator(File fromRoot, File toRoot) {
		this.fromRoot = fromRoot;
		this.toRoot = toRoot;
		compare();
	}

	private void compare() {
		try {
			compare(fromRoot, toRoot);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	private void compare(File from, File to) throws IOException {
		findRemovedFiles(from, to);

		for (File child : from.listFiles()) {
			addedOrChanged(child, new File(to, child.getName()));
		}
	}

	public void printChanges() {
		for (String value : changes.values()) {
			System.out.println(value);
		}
	}

	public void printDiff() {
		for (String value : diff.values()) {
			System.out.println(value);
		}
	}

	private void addedOrChanged(File expecting, File counterpart) throws IOException {
		if (expecting.isDirectory()) {
			if (!counterpart.isDirectory()) {
				change("+\t", DIRECTORY, counterpart);
				return;
			}
			compare(expecting, counterpart);
			return;
		}
		if (!counterpart.isFile()) {
			change("+\t", FILE, counterpart);
			return;
		}
		if (!FileCompare.contentEquals(expecting, counterpart)) {
			change(">>\t", FILE, counterpart);
			String path = relativeToToRoot(counterpart);
			diff.put(path, Str.sub("$\ndiff $ $\n", expecting.getName(), diffPath(expecting), diffPath(counterpart)));
		}
	}

	private void findRemovedFiles(File from, File to) {
		Set<String> found = SetTool.of(to.list());
		for (String expecting : from.list()) {
			found.remove(expecting);
		}
		for (String removed : found) {
			File file = new File(to, removed);
			change("-\t", file.isFile(), file);
		}
	}

	private String diffPath(File child) {
		String path = child.getAbsolutePath();
		return path.contains(" ") ? '"' + path + '"' : path;
	}

	private void change(String prefix, boolean isFile, File file) {
		String path = relativeToToRoot(file);
		String type = isFile ? " (file)" : " (dir)";
		changes.put(path, prefix + path + type);
	}

	private String relativeToToRoot(File file) {
		try {
			return file.getCanonicalPath().substring(fromRoot.getCanonicalPath().length());
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}
}