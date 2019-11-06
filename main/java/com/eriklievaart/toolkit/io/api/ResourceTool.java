package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ValidatingMap;

public class ResourceTool {

	/**
	 * Checks whether or not the resource denotes by the specified path is available.
	 *
	 * @param loader
	 *            use this class to get the ClassLoader.
	 */
	public static boolean isAvailable(Class<?> loader, String res) {
		return getURL(loader, res) != null;
	}

	/**
	 * Checks whether or not the resource denotes by the specified path is available.
	 */
	public static boolean isAvailable(String res) {
		return isAvailable(getLiteral(), res);
	}

	public static ValidatingMap<String, String> loadProperties(String res) {
		return loadProperties(getLiteral(), res);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ValidatingMap<String, String> loadProperties(Class<?> loader, String res) {
		checkResourceString(res);

		InputStream is = ResourceTool.getInputStream(loader, res);
		Check.notNull(is, "% not found", res);

		return new ValidatingMap(res, PropertiesIO.load(is));
	}

	/**
	 * Get resource as InputStream using the ClassLoader for this class.
	 *
	 * @param res
	 *            path to the resource.
	 */
	public static InputStream getInputStream(String res) {
		return getInputStream(getLiteral(), res);
	}

	/**
	 * Get resource as InputStream using the ClassLoader associated with parameter loader.
	 *
	 * @param loader
	 *            use this class to get the ClassLoader.
	 * @param res
	 *            path to the resource.
	 */
	public static InputStream getInputStream(Class<?> loader, String res) {
		checkResourceString(res);
		try {
			InputStream stream = loader.getResourceAsStream(res);
			Check.notNull(stream, "not a valid resource; % was <null>", res);
			return stream;
		} catch (Exception e) {
			throw resourceException(e, res);
		}
	}

	public static String getString(String res) {
		return getString(getLiteral(), res);
	}

	public static String getString(Class<?> loader, String res) {
		return StreamTool.toString(getInputStream(loader, res));
	}

	public static List<String> readLines(String res) {
		return readLines(getLiteral(), res);
	}

	public static List<String> readLines(Class<?> loader, String res) {
		return StreamTool.readLines(getInputStream(loader, res));
	}

	public static URL getURL(String res) {
		return getURL(getLiteral(), res);
	}

	public static URL getURL(Class<?> loader, String res) {
		checkResourceString(res);
		return loader.getResource(res);
	}

	public static File getFile(String res) {
		return getFile(getLiteral(), res);
	}

	public static File getFile(Class<?> loader, String res) {
		return getOptionalFile(loader, res).get();
	}

	public static Optional<File> getOptionalFile(String res) {
		return getOptionalFile(getLiteral(), res);
	}

	public static Optional<File> getOptionalFile(Class<?> loader, String res) {
		try {
			URL url = getURL(loader, res);
			return url == null ? Optional.empty() : Optional.of(new File(URLDecoder.decode(url.getFile(), "UTF-8")));
		} catch (Exception e) {
			throw resourceException(e, res);
		}
	}

	private static void checkResourceString(String res) {
		Check.matches(res, "/.*", "% resource path must start with a slash", res);
	}

	private static Class<?> getLiteral() {
		return ResourceTool.class;
	}

	private static RuntimeIOException resourceException(Exception e, String res) {
		return new RuntimeIOException("% could not be read; $", e, res, e.getMessage());
	}
}
