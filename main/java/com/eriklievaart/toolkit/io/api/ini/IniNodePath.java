package com.eriklievaart.toolkit.io.api.ini;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class IniNodePath {
	private String[] path;

	public IniNodePath(String path) {
		Check.notBlank(path);
		this.path = path.split("/", 2);
	}

	public boolean isNested() {
		return path.length > 1;
	}

	public String getHead() {
		return path[0];
	}

	public String getTail() {
		return path[1];
	}
}
