package com.eriklievaart.toolkit.logging.api.appender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.LogRecord;

/**
 * Simple Appender for writing to a file. This class is thread safe, but must have only one instance per file and may
 * cause conflicts when multiple VM's write to the same file.
 */
public class SimpleFileAppender extends AbstractAppender {

	private FileChannel channel;

	@SuppressWarnings("resource")
	public SimpleFileAppender(File file) throws FileNotFoundException {
		file.getParentFile().mkdirs();
		this.channel = new FileOutputStream(file, true).getChannel();
	}

	@Override
	public synchronized void append(LogRecord record) {
		String message = format(record) + "\n";
		try {
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(message.getBytes());
			buf.flip();

			while (buf.hasRemaining()) {
				channel.write(buf);
			}
			channel.force(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			channel.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}