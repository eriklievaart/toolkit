package com.eriklievaart.toolkit.vfs.api.file;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class MemoryFileSystem {
	private static final String ROOT = "/";

	private Map<String, MemoryFile> roots = new Hashtable<>();

	public MemoryFile resolve(String url) {
		String path = normalize(UrlTool.getPath(url));
		if (Str.isBlank(path)) {
			return null;
		}
		if (UrlTool.getPath(url).equals(ROOT)) {
			return new MemoryFile(ROOT, this);
		}
		if (UrlTool.isWindowsPath(url) && !UrlTool.isWindowsPath(path)) {
			path = url.substring(0, 2) + path;
		}
		String head = UrlTool.getHead(path);
		String prefixSlash = head.matches("[a-zA-Z]:") ? head : "/" + head;
		MemoryFile current = roots.containsKey(head) ? roots.get(head) : new MemoryFile(prefixSlash, this);
		roots.put(head, current);

		return appendPath(current, UrlTool.getTail(path));
	}

	private MemoryFile appendPath(MemoryFile root, String path) {
		if (Str.isBlank(path)) {
			return root;
		}
		String head = UrlTool.getHead(path);
		String tail = UrlTool.getTail(path);
		return appendPath(root.getOrCreateChild(head), tail);
	}

	List<MemoryFile> getRoots() {
		List<MemoryFile> result = new ArrayList<>(roots.values());
		return result;
	}

	private String normalize(String path) {
		return Str.isBlank(path) ? null : Paths.get(path).normalize().toString();
	}

	public void delete(MemoryFile file) {
		MemoryFile parent = file.getParentFile().get();
		Iterator<MemoryFile> iterator = parent.children.iterator();
		while (iterator.hasNext()) {
			VirtualFile child = iterator.next();
			if (child.getPath().equals(file.getPath())) {
				iterator.remove();
			}
		}
		roots.remove(file.getPath());
	}
}