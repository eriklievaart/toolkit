package com.eriklievaart.toolkit.io.api;

import java.io.File;

/**
 * Utility class for figuring out where the jar or classes folder of any class is located.
 *
 * @author Erik Lievaart
 */
public class JvmPaths {

	private JvmPaths() {
	}

	/**
	 * Get the location of the jar file, of the directory where the class files are located.
	 */
	public static String getJarFileOrClassDir(final Class<?> clazz) {
		return clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
	}

	/**
	 * Get the directory of the jar file or the directory of the classes.
	 */
	public static String getJarDirOrClassDir(final Class<?> clazz) {
		String location = getJarFileOrClassDir(clazz);
		if (new File(location).isFile()) {
			return UrlTool.getParent(location);
		}
		return location;
	}

	/**
	 * Get the directory of the jar file or the run directory.
	 */
	public static String getJarDirOrRunDir(final Class<?> clazz) {
		String location = getJarFileOrClassDir(clazz);
		if (new File(location).isFile()) {
			return UrlTool.getParent(location);
		}
		return new File("").getAbsolutePath();
	}
}