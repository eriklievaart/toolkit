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

	private static final String NULL_RESOURCE_MESSAGE = "<null> is not a valid resource";

	public static ValidatingMap<String, String> loadProperties(String res) {
		checkResourceString(res);

		InputStream is = ResourceTool.getInputStream(res);
		Check.notNull(is, "% not found", res);

		return new ValidatingMap(res, PropertiesIO.load(is));
	}

	public static InputStream getInputStream(String res) {
		checkResourceString(res);
		try {
			InputStream stream = getLiteral().getResourceAsStream(res);
			Check.notNull(stream, NULL_RESOURCE_MESSAGE);
			return stream;
		} catch (Exception e) {
			throw resourceException(e, res);
		}
	}

	private static RuntimeIOException resourceException(Exception e, String res) {
		return new RuntimeIOException("% could not be read; $", e, res, e.getMessage());
	}

	public static String getString(String res) {
		return StreamTool.toString(getInputStream(res));
	}

	public static List<String> readLines(String res) {
		return StreamTool.readLines(getInputStream(res));
	}

	public static URL getURL(String res) {
		checkResourceString(res);
		return getLiteral().getResource(res);
	}

	public static File getFile(String res) {
		return getOptionalFile(res).get();
	}

	public static Optional<File> getOptionalFile(String res) {
		try {
			URL url = getURL(res);
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

}
