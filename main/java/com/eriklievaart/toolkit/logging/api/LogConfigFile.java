package com.eriklievaart.toolkit.logging.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;

import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.logging.api.level.LogLevelTool;

public class LogConfigFile {
	public static void load(File file) {
		try (InputStream is = new FileInputStream(file)) {
			load(is);

		} catch (Exception e) {
			throw new RuntimeIOException(e);
		}
	}

	private static void load(InputStream is) {
		new LineFilter(is).dropHash().dropBlank().eof().iterate((number, line) -> {
			AssertionException.on(!line.contains("="), "invalid line($): $", number, line);

			String[] loggerToLevel = line.split("=");
			String pkg = loggerToLevel[0];
			Level level = LogLevelTool.toLevel(loggerToLevel[1].trim());

			LogConfig.setLevel(pkg, level);
		});
	}
}
