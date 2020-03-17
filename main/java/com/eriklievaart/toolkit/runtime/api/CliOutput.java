package com.eriklievaart.toolkit.runtime.api;

/**
 * This interface can be used to redirect output from the native command line using the strategy design pattern.
 *
 * @author Erik Lievaart
 */
public interface CliOutput {

	/**
	 * Redirect a single line of output from the native command line.
	 *
	 * @param line
	 *            The output.
	 */
	public void println(String line);
}