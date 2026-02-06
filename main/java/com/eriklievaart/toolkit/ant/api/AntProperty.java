package com.eriklievaart.toolkit.ant.api;

import java.io.File;
import java.io.IOException;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.SystemProperties;
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

		try {
			if (Str.notBlank(property)) {
				return getDirectory();
			}
			File local = new File(fallback);
			String root = new File("").getCanonicalPath();
			Check.isTrue(local.isDirectory(), "% is not a directory in $", fallback, root);
			return local;

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public File getDirectoryOrHomeDir(String path) {
		CheckStr.notBlank(path);

		if (Str.notBlank(property)) {
			return getDirectory();
		}
		File fallback = new File(SystemProperties.getHomeDir(), path);
		CheckFile.isDirectory(fallback);
		return fallback;
	}

	private void check() {
		Check.notBlank(property, Str.sub("property % not set", name));
	}

	public boolean isSet() {
		return Str.notBlank(property);
	}
}