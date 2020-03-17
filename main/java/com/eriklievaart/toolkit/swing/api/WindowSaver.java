package com.eriklievaart.toolkit.swing.api;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import com.eriklievaart.toolkit.io.api.JvmPaths;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.io.api.properties.PropertiesIO;
import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Utility class for saving and restoring the positions of all Window's used in the application. Simply call the
 * {@link WindowSaver#initialize()} method and all Window positions will be saved and restored automatically. Be sure to
 * call the setName() method on any window before making them visible.
 *
 * @author Erik Lievaart
 */
public class WindowSaver implements AWTEventListener {

	private static final WindowSaver INSTANCE = new WindowSaver();

	private static final String JAR_DIR_OR_RUN_DIR = JvmPaths.getJarDirOrRunDir(WindowSaver.class);
	private static final String PROPERTIES_FILE_PATH = UrlTool.append(JAR_DIR_OR_RUN_DIR, "data/WindowSaver.ini");

	private static final AtomicReference<File> CONFIG_FILE = new AtomicReference<>();

	private final Map<String, JFrame> framemap = new HashMap<>();

	private WindowSaver() {

	}

	private static WindowSaver instance() {
		return INSTANCE;
	}

	/**
	 * Save and restore all Window positions automatically (store in default location).
	 */
	public static void initialize() {
		initialize(new File(PROPERTIES_FILE_PATH));
	}

	/**
	 * Save and restore all Window positions automatically.
	 *
	 * @param configFile
	 *            Store settings here.
	 */
	public static void initialize(File configFile) {
		boolean changed = CONFIG_FILE.compareAndSet(null, configFile);
		Check.isTrue(changed, "Cannot initialize twice!");
		configFile.getParentFile().mkdirs();
		Toolkit.getDefaultToolkit().addAWTEventListener(instance(), AWTEvent.WINDOW_EVENT_MASK);
	}

	public static void shutdown() {
		CONFIG_FILE.set(null);
		Toolkit.getDefaultToolkit().removeAWTEventListener(instance());
	}

	@Override
	public void eventDispatched(final AWTEvent evt) {
		if (evt.getID() == WindowEvent.WINDOW_OPENED) {
			ComponentEvent cev = (ComponentEvent) evt;
			if (cev.getComponent() instanceof JFrame) {
				loadSettings((JFrame) cev.getComponent());
			}
		}
		if (evt.getID() == WindowEvent.WINDOW_CLOSED) {
			saveSettings();
		}
		if (evt.getID() == WindowEvent.WINDOW_CLOSING) {
			saveSettings();
		}
	}

	private static void loadSettings(final JFrame frame) {
		Properties settings = loadProperties();
		String name = frame.getName();
		checkFrameName(name);

		int x = getInt(settings, name + ".x", 100);
		int y = getInt(settings, name + ".y", 100);
		int w = getInt(settings, name + ".w", 440);
		int h = getInt(settings, name + ".h", 440);
		frame.setBounds(x, y, w, h);
		INSTANCE.framemap.put(name, frame);
		frame.validate();
	}

	private static void checkFrameName(final String name) {
		if (name.matches("frame\\d")) {
			throw new IllegalArgumentException("Please call setName(String) on all JFrames");
		}
	}

	private static Properties loadProperties() {
		try {
			if (CONFIG_FILE.get().isFile()) {
				return PropertiesIO.load(CONFIG_FILE.get());
			}
		} catch (RuntimeIOException ignore) {
		}
		return new Properties();
	}

	private static int getInt(final Properties props, final String name, final int def) {
		String value = props.getProperty(name);
		if (value == null) {
			return def;
		}
		return Integer.parseInt(value);
	}

	private static void saveSettings() {
		try {
			PropertiesIO.store(prepareData(), CONFIG_FILE.get());
		} catch (RuntimeIOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static Properties prepareData() {
		Properties settings = loadProperties();

		for (String name : INSTANCE.framemap.keySet()) {
			checkFrameName(name);

			JFrame frame = INSTANCE.framemap.get(name);
			settings.setProperty(name + ".x", "" + frame.getX());
			settings.setProperty(name + ".y", "" + frame.getY());
			settings.setProperty(name + ".w", "" + frame.getWidth());
			settings.setProperty(name + ".h", "" + frame.getHeight());
		}
		return settings;
	}
}