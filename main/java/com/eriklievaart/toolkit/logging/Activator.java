package com.eriklievaart.toolkit.logging;

import java.io.File;
import java.util.function.Consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.LogConfig;
import com.eriklievaart.toolkit.logging.api.LogConfigFile;
import com.eriklievaart.toolkit.logging.api.format.DatedFormatter;
import com.eriklievaart.toolkit.logging.api.format.SimpleFormatter;

public class Activator implements BundleActivator {
	private static final String FORMAT_PROPERTY = "com.eriklievaart.toolkit.logging.date";
	private static final String CONFIG_DIR_PROPERTY = "com.eriklievaart.toolkit.logging.config.dir";
	private static final String CONFIG_FILE_PROPERTY = "com.eriklievaart.toolkit.logging.config.file";

	@Override
	public void start(BundleContext context) throws Exception {
		ifProperty(context, FORMAT_PROPERTY, value -> {
			installFormat(value);
		});
		ifProperty(context, CONFIG_DIR_PROPERTY, value -> {
			loadConfigDir(value);
		});
		ifProperty(context, CONFIG_FILE_PROPERTY, value -> {
			loadConfigFile(value);
		});
	}

	private void ifProperty(BundleContext context, String property, Consumer<String> consumer) {
		String value = context.getProperty(property);
		if (Str.notBlank(value)) {
			consumer.accept(value);
		}
	}

	// example format: dd-MM-yyyy HH:mm:ss
	private void installFormat(String format) {
		System.out.println("INFO toolkit-logging.Activator logging format = " + format);
		LogConfig.setDefaultFormatter(new DatedFormatter(format, new SimpleFormatter()));
	}

	private void loadConfigDir(String path) {
		System.out.println("INFO toolkit-logging.Activator config dir = " + path);
		File file = new File(path);
		CheckFile.isDirectory(file);
		LogConfigFile.initFromDirectory(file);
	}

	private void loadConfigFile(String path) {
		System.out.println("INFO toolkit-logging.Activator config file = " + path);
		File file = new File(path);
		CheckFile.isFile(file);
		LogConfigFile.load(file);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LogConfig.closeAllAppenders();
	}
}