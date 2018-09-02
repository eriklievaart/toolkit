package com.eriklievaart.toolkit.swing.api;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 * Utility methods for running code in the Swing event Thread.
 * 
 * @author Erik Lievaart
 */
public class SwingThread {
	private SwingThread() {
	}

	/**
	 * Run a runnable in the Swing event Thread.
	 * 
	 * @see SwingUtilities#invokeLater(Runnable)
	 */
	public static void invokeLater(final Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
			return;
		}
		SwingUtilities.invokeLater(runnable);
	}

	/**
	 * Run a runnable in the Swing event Thread.
	 * 
	 * @see SwingUtilities#invokeAndWait(Runnable)
	 */
	public static void invokeAndWait(final Runnable runnable) throws InterruptedException, InvocationTargetException {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
			return;
		}
		SwingUtilities.invokeAndWait(runnable);
	}

	/**
	 * Run a runnable in the Swing event Thread and convert any Exceptions to {@link RuntimeException}'s.
	 * 
	 * @see SwingUtilities#invokeAndWait(Runnable)
	 */
	public static void invokeAndWaitUnchecked(final Runnable runnable) {
		try {
			invokeAndWait(runnable);
		} catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
