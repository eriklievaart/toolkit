package com.eriklievaart.toolkit.logging.api.appender;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatFactory;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatWrapper;
import com.eriklievaart.toolkit.lang.api.date.TimestampTool;

/**
 * Simple Appender for writing to a file. This class is thread safe, but must have only one instance per file and may
 * cause conflicts when multiple VM's write to the same file.
 */
public class RotatingFileAppender extends AbstractAppender {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final long INTERVAL = TimestampTool.ONE_DAY;

	private AtomicBoolean closed = new AtomicBoolean(false);
	private AtomicLong validUntil = new AtomicLong();
	private AtomicReference<SimpleFileAppender> delegate = new AtomicReference<>();
	private final File root;
	private final String suffix;

	/**
	 * @param file
	 *            filename without date prefix
	 */
	public RotatingFileAppender(File file) {
		this(file.getParentFile(), file.getName());
	}

	/**
	 * @param dir
	 *            directory to write log files.
	 * @param name
	 *            the name of the file without the date prefix.
	 */
	public RotatingFileAppender(File dir, String name) {
		Check.notNull(dir, name);
		this.root = dir;
		this.suffix = name;
	}

	@Override
	protected void write(LogRecord record) {
		Check.isFalse(closed.get(), "Appender has been closed");
		if (System.currentTimeMillis() > validUntil.get()) {
			createAppender();
		}
		delegate.get().append(record);
	}

	private synchronized void createAppender() {
		long stamp = System.currentTimeMillis();
		if (stamp < validUntil.get()) {
			return;
		}
		SimpleDateFormatWrapper format = SimpleDateFormatFactory.getWrapper(DATE_FORMAT);
		String date = format.toString(stamp);
		File file = new File(root, date + "-" + suffix);
		try {
			SimpleFileAppender appender = new SimpleFileAppender(file);
			delegate.set(appender);

			String next = format.toString(stamp + INTERVAL);
			validUntil.set(format.toTimestamp(next));

		} catch (Exception e) {
			throw new RuntimeIOException(e);
		}
	}

	@Override
	public void close() {
		closed.set(true);
		closeAppender();
	}

	private void closeAppender() {
		SimpleFileAppender appender = delegate.get();
		if (appender != null) {
			appender.close();
		}
	}
}
