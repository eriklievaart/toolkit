package com.eriklievaart.toolkit.runtime.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Invokes {@link CliCommand} objects on the native command line.
 *
 * @author Erik Lievaart
 */
public class CliInvoker {

	private static CliStreams streams = new CliStreams();

	private CliInvoker() {
	}

	/**
	 * Invokes the supplied {@link CliCommand}.
	 */
	public static boolean invoke(final CliCommand command) {
		return invoke(command, new File(System.getProperty("java.io.tmpdir")));
	}

	/**
	 * Invokes the supplied {@link CliCommand} in the specified directory.
	 *
	 * @return true on success, false if the exit code was not 0 or an Exception occurred.
	 */
	public static boolean invoke(final CliCommand command, final File dir) {
		return invoke(command, dir, streams);
	}

	public static boolean invoke(CliCommand command, File dir, CliStreams output) {
		try {
			int exit = execute(command, dir, output);
			output.debug("command exited with code: " + exit);
			return exit == 0;

		} catch (RuntimeIOException ioe) {
			output.thrown(ioe);
			return false;
		}
	}

	private static int execute(final CliCommand command, final File dir, CliStreams output) {
		try {
			Check.notNull(command, dir);
			output.debug(command.toString());
			Process process = Runtime.getRuntime().exec(command.cmd(), null, dir);

			// Reading the streams from the Process prevents the task from hanging.
			dumpInNewThread(process.getErrorStream(), output.getErrorOutput());
			dumpInCurrentThread(process.getInputStream(), output.getNormalOutput());
			return process.waitFor();

		} catch (InterruptedException | IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	/**
	 * Read the stream in a new Thread.
	 */
	private static void dumpInNewThread(final InputStream input, final CliOutput output) {
		new Thread(new StreamRunnable(input, output)).start();
	}

	/**
	 * Read the stream in the currently active Thread.
	 */
	private static void dumpInCurrentThread(final InputStream input, final CliOutput output) {
		new StreamRunnable(input, output).run();
	}
}