package com.eriklievaart.toolkit.logging.api;

import java.io.File;

import com.eriklievaart.toolkit.logging.api.appender.RotatingFileAppender;
import com.eriklievaart.toolkit.logging.api.format.DatedFormatter;
import com.eriklievaart.toolkit.logging.api.format.SimpleFormatter;

public class RotatingFileAppenderManualTest {

	public static void main(String[] args) {
		LogConfig.setDefaultAppenders(new RotatingFileAppender(new File("/tmp/test"), "file.log"));
		LogConfig.setDefaultFormatter(new DatedFormatter("yyyy-dd-MM HH:mm:ss", new SimpleFormatter()));
		LogTemplate log = new LogTemplate(RotatingFileAppenderManualTest.class);
		for (int i = 0; i < 2000; i++) {
			log.debug("test " + System.currentTimeMillis());
		}
	}
}