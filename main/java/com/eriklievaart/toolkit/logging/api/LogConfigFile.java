package com.eriklievaart.toolkit.logging.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.Console;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.logging.api.level.LogLevelTool;

public class LogConfigFile {
	private static List<String> locations = ListTool.of("logging.ini", "logging.properties");

	public static void initFromDirectory(File directory) {
		getOptionalInitFile(directory).ifPresent(LogConfigFile::load);
	}

	private static Optional<File> getOptionalInitFile(File directory) {
		for (String location : locations) {
			File file = new File(directory, location);
			if (file.isFile()) {
				return Optional.of(file);
			}
		}
		Console.println("\nWARN toolkit-logging.LogConfigFile missing file!\n$ $\n", directory, locations);
		return Optional.empty();
	}

	public static void load(File file) {
		CheckFile.isFile(file);
		System.out.println("INFO toolkit-logging.LogConfigFile file=" + file);
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
