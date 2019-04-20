package com.eriklievaart.toolkit.ant.api;

import java.io.File;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class AntProperty {

	private String property;
	private final String name;

	public AntProperty(String name) {
		this.name = name;
		property = System.getProperty(name);
	}

	public String getString() {
		check();
		return property;
	}

	public File getDirectory() {
		File file = new File(getString());
		Check.isTrue(file.isDirectory(), "% is not a directory: %", name, getString());
		return file;
	}

	public File getDirectoryOrFallbackOn(String fallback) {
		CheckStr.notBlank(fallback);

		if (Str.isBlank(property)) {
			File local = new File(fallback);
			Check.isTrue(local.isDirectory(), "% is not a directory", fallback);
			return local;
		}
		return getDirectory();
	}

	private void check() {
		Check.notBlank(property, Str.sub("property % not set", name));
	}

	public boolean isSet() {
		return Str.notBlank(property);
	}
}
