package com.eriklievaart.toolkit.logging.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.appender.Appender;
import com.eriklievaart.toolkit.logging.api.appender.ConsoleAppender;
import com.eriklievaart.toolkit.logging.api.format.SimpleFormatter;

public class LogConfig {

	private static AtomicReference<Formatter> formatterReference = new AtomicReference<>();
	private static Map<String, List<Appender>> appenders = NewCollection.concurrentMap();
	private static LazyMap<String, Logger> loggers = new LazyMap<>(id -> new Logger(id));

	static {
		init();
	}

	static synchronized void init() {
		closeAllAppenders();
		appenders.clear();
		loggers.clear();
		// sensible defaults
		formatterReference.set(new SimpleFormatter());
		installDefaultRootAppenders("");
	}

	public static void closeAllAppenders() {
		appenders.values().forEach(list -> list.forEach(Appender::close));
	}

	public static synchronized Logger getLogger(String name) {
		return loggers.get(name);
	}

	public static synchronized void setDefaultFormatter(Formatter formatter) {
		LogConfig.formatterReference.set(formatter);
	}

	public static synchronized Formatter getDefaultFormatter() {
		return formatterReference.get();
	}

	public static synchronized void setDefaultAppenders(Appender... appenders) {
		LogConfig.appenders.put("", Collections.unmodifiableList(new CopyOnWriteArrayList<>(Arrays.asList(appenders))));
	}

	public static List<Appender> getDefaultAppenders() {
		return appenders.get("");
	}

	public static List<Appender> getAppenders(String logger) {
		List<Appender> result = appenders.get(logger);
		return result != null ? result : getAppenders(getParent(logger));
	}

	static String getParent(String logger) {
		return logger.contains(".") ? logger.replaceFirst(".[^.]*$", "") : "";
	}

	public static void setAppenders(String logger, Appender... list) {
		setAppenders(logger, Arrays.asList(list));
	}

	public static void setAppenders(String logger, List<Appender> list) {
		appenders.put(logger, Collections.unmodifiableList(new CopyOnWriteArrayList<>(list)));
	}

	public static void installDefaultRootAppenders(String logger) {
		if (Str.isBlank(logger)) {
			appenders.put("", Arrays.asList(new ConsoleAppender()));
		}
	}
}
