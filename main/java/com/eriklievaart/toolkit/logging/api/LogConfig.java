package com.eriklievaart.toolkit.logging.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.logging.Level;

import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.appender.Appender;
import com.eriklievaart.toolkit.logging.api.appender.ConsoleAppender;
import com.eriklievaart.toolkit.logging.api.format.SimpleFormatter;
import com.eriklievaart.toolkit.logging.api.level.LogLevelConfig;

public class LogConfig {

	private static final AtomicReference<Formatter> FORMATTER_REFERENCE = new AtomicReference<>();
	private static final ThreadPoolExecutor EXECUTOR = initExecutor();
	private static Supplier<Executor> executorSupplier;
	private static LogLevelConfig levels = new LogLevelConfig();
	private static Map<String, List<Appender>> appenders = NewCollection.concurrentMap();
	private static LazyMap<String, Logger> loggers = new LazyMap<>(id -> new Logger(id, levels));

	static {
		init();
	}

	private static ThreadPoolExecutor initExecutor() {
		return new ThreadPoolExecutor(0, 10, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
	}

	static synchronized void init() {
		closeAllAppenders();
		appenders.clear();
		loggers.clear();
		// sensible defaults
		FORMATTER_REFERENCE.set(new SimpleFormatter());
		installDefaultRootAppenders("");

		if (executorSupplier == null) {
			executorSupplier = () -> EXECUTOR;
		}
	}

	public static void setSameThreadLogging() {
		executorSupplier = () -> task -> task.run();
	}

	static Executor getLoggingExecutor() {
		return executorSupplier.get();
	}

	public static void closeAllAppenders() {
		appenders.values().forEach(list -> list.forEach(Appender::close));
	}

	static synchronized Logger getLogger(String name) {
		return loggers.get(name);
	}

	public static synchronized void setDefaultFormatter(Formatter formatter) {
		LogConfig.FORMATTER_REFERENCE.set(formatter);
	}

	public static synchronized Formatter getDefaultFormatter() {
		return FORMATTER_REFERENCE.get();
	}

	public static synchronized void setDefaultAppenders(Appender... appenders) {
		setDefaultAppenders(Arrays.asList(appenders));
	}

	public static synchronized void setDefaultAppenders(List<Appender> list) {
		LogConfig.appenders.put("", Collections.unmodifiableList(new CopyOnWriteArrayList<>(list)));
	}

	public static List<Appender> getDefaultAppenders() {
		return appenders.get("");
	}

	public static List<Appender> getAppenders(String logger) {
		List<Appender> result = appenders.get(logger);
		return result != null ? result : getAppenders(getParent(logger));
	}

	public static String getParent(String logger) {
		return logger.contains(".") ? logger.replaceFirst("\\.[^.]++$", "") : "";
	}

	public static void setAppenders(String logger, Appender... list) {
		setAppenders(logger, Arrays.asList(list));
	}

	public static void setAppenders(String logger, List<Appender> list) {
		appenders.put(logger, Collections.unmodifiableList(new CopyOnWriteArrayList<>(list)));
	}

	public static void setLevel(String pkg, Level level) {
		levels.setLevel(pkg, level);
	}

	public static void installDefaultRootAppenders(String logger) {
		if (Str.isBlank(logger)) {
			appenders.put("", Arrays.asList(new ConsoleAppender()));
		}
	}
}
