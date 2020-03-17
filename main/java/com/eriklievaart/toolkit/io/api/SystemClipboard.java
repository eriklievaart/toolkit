package com.eriklievaart.toolkit.io.api;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import com.eriklievaart.toolkit.logging.api.LogTemplate;

/**
 * Utility class for accessing the OS clipboard.
 *
 * @author Erik Lievaart
 */
public class SystemClipboard {
	private static final LogTemplate log = new LogTemplate(SystemClipboard.class);

	private SystemClipboard() {
	}

	/**
	 * Write a simple String to the system clipboard.
	 */
	public static void writeString(final String data) {
		writeTransferable(new StringSelection(data));
	}

	/**
	 * Write a simple String to the selection clipboard.
	 */
	public static void writeSelection(final String data) {
		Toolkit.getDefaultToolkit().getSystemSelection().setContents(new StringSelection(data), null);
	}

	/**
	 * Write a Transferable Object to the system clipboard.
	 */
	public static void writeTransferable(final Transferable data) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, null);
	}

	/**
	 * Read the contents of the system clipboard as a String.
	 */
	public static String readString() {
		return readFlavor(DataFlavor.stringFlavor);
	}

	/**
	 * Read the contents of the selection clipboard as a String.
	 */
	public static String readSelection() {
		return readFlavor(DataFlavor.stringFlavor, Toolkit.getDefaultToolkit().getSystemSelection());
	}

	/**
	 * Try to read the contents of the clipboard in the supplied flavor.
	 *
	 * @param <E>
	 *            auto cast the resulting data to this type.
	 * @param flavor
	 *            Flavor to read from the clipboard.
	 * @return the contents of the clipboard, or null if no data is available in this flavor.
	 */
	public static <E> E readFlavor(final DataFlavor flavor) {
		return readFlavor(flavor, Toolkit.getDefaultToolkit().getSystemClipboard());
	}

	@SuppressWarnings("unchecked")
	private static <E> E readFlavor(final DataFlavor flavor, Clipboard clipboard) {
		try {
			return (E) clipboard.getData(flavor);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * List all available flavors for the data currently on the clipboard.
	 */
	public static DataFlavor[] getDataFlavors() {
		return Toolkit.getDefaultToolkit().getSystemClipboard().getAvailableDataFlavors();
	}

	/**
	 * Log all available flavors.
	 */
	public static void dumpFlavors() {
		for (DataFlavor flavor : getDataFlavors()) {
			log.info(flavor.toString());
		}
	}
}