package com.eriklievaart.toolkit.io.api;

import java.util.Map;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.http.UrlParameterEncoder;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

/**
 * Utility class for working with URL String representations.
 *
 * @author Erik Lievaart
 */
@ThreadSafe
public class UrlTool {

	private static final String SLASH = "/";
	private static final String BACKSLASH = "\\";

	private static Map<Character, String> escapes = NewCollection.concurrentMap();
	private static Map<String, Character> unescapes;

	static {
		escapes.put(' ', "%20");
		escapes.put('<', "%3C");
		escapes.put('>', "%3E");
		escapes.put('#', "%23");
		escapes.put('%', "%25");
		escapes.put('{', "%7B");
		escapes.put('}', "%7D");
		escapes.put('|', "%7C");
		escapes.put('\\', SLASH);
		escapes.put('^', "%5E");
		escapes.put('~', "%7E");
		escapes.put('[', "%5B");
		escapes.put(']', "%5D");
		escapes.put('`', "%60");
		escapes.put(';', "%3B");
		escapes.put('?', "%3F");
		escapes.put('@', "%40");
		escapes.put('=', "%3D");
		escapes.put('&', "%26");
		escapes.put('$', "%24");

		unescapes = MapTool.reverse(escapes);
	}

	private UrlTool() {
	}

	/**
	 * Get the name part of a URL. Example file:///tmp/file.ext => file.ext
	 */
	public static String getName(final String url) {
		return PatternTool.getGroup1(".*?([^\\\\/]*+[\\\\/]?)", url);
	}

	/**
	 * Get the extension part of a URL. Example file:///tmp/file.ext => ext
	 */
	public static String getExtension(final String url) {
		Check.notNull(url);
		String file = getName(url);
		if (file.lastIndexOf('.') == 0) {
			return "";
		}
		String ext = PatternTool.getGroup1("^.*\\.([^.]++)$", file);
		return ext == null ? "" : ext;
	}

	/**
	 * Get the base name name part of a URL. Example file:///tmp/file.ext => file
	 */
	public static String getBaseName(final String url) {
		String file = getName(url);
		return file.lastIndexOf('.') == 0 ? file : PatternTool.getGroup1("^(.+?)(?:\\.[^.]++)?$", file);
	}

	/**
	 * Get the path part of a URL. Example file:///tmp/file.ext => /tmp/file.ext
	 */
	public static String getPath(final String url) {
		String startsWithSlash = PatternTool.getGroup1("^[a-zA-Z]++:(?://)?(.*+)$", url);
		if (startsWithSlash == null) {
			return url;
		}
		return PatternTool.matches("/[a-zA-Z]:/.*+", startsWithSlash) ? startsWithSlash.substring(1) : startsWithSlash;
	}

	/**
	 * Get the domain of an URL if there is one. Returns null otherwise.
	 */
	public static String getDomain(String url) {
		if (url.contains(":")) {
			return url.replaceFirst("[^:]++:/++", "").replaceFirst("/.*+", "");
		}
		return url.startsWith(SLASH) ? null : url.replaceFirst("/.++", "");
	}

	/**
	 * Get the parent of a URL. Example file:///tmp/file.ext => file:///tmp
	 */
	public static String getParent(final String url) {
		return PatternTool.getGroup1("(.*?)[^\\\\/]++[\\\\/]?", url);
	}

	/**
	 * Remove the last trailing slash of an URL if there is one. Example: file:///tmp/ => file:///tmp
	 */
	public static String removeTrailingSlash(final String url) {
		String regex = "^((?:([a-zA-Z]++:)?/{1,3}(?:[a-zA-Z]:[/\\\\])?)?.*?)[\\/]?$";
		return url == null ? null : PatternTool.getGroup1(regex, unix(url));
	}

	/**
	 * Remove all slashes at the start of a String
	 */
	public static String removeLeadingSlashes(final String url) {
		return url == null ? null : url.replaceFirst("^[/\\\\]++", "");
	}

	/**
	 * Add a trailing slash to an URL if there is none. Example: file:///tmp => file:///tmp/
	 */
	public static String addSlash(final String path) {
		return path.endsWith(SLASH) || path.endsWith(BACKSLASH) ? path : path + SLASH;
	}

	/**
	 * Replace escape characters in an URL with their escape sequence. Example file:///tmp/has space =>
	 * file:///tmp/has%20space
	 */
	public static String escape(final String url) {
		StringBuilder builder = new StringBuilder(url);
		for (int i = 0; i < builder.length(); i++) {
			if (escapes.containsKey(builder.charAt(i))) {
				builder.replace(i, i + 1, escapes.get(builder.charAt(i)));
			}
		}
		return builder.toString();
	}

	/**
	 * Replace escaped sequences in an URL with their original characters. Example file:///tmp/has%20space =>
	 * file:///tmp/has space
	 */
	public static String unescape(final String escaped) {
		StringBuilder builder = new StringBuilder(escaped);
		for (int i = 0; i < builder.length(); i++) {
			if (builder.charAt(i) == '%') {
				String escape = builder.substring(i, Math.min(i + 3, builder.length()));
				Check.isTrue(unescapes.containsKey(escape), "Invalid escape sequence: % in %", escape, escaped);
				builder.delete(i, i + 3).insert(i, unescapes.get(escape));
			}
		}
		return builder.toString();
	}

	/**
	 * Append a child path to an URL. Ensures their is exactly one slash between the url and the child.
	 */
	public static String append(final String url, final String child) {
		Check.notNull(url);
		Check.notNull(child);

		return addSlash(url) + Str.emptyOnNull(PatternTool.getGroup1("[/\\\\]?+(.++)", child));
	}

	/**
	 * Creates an URL by appending the specified paths. Ensures their is exactly one slash between the every path and
	 * the next one.
	 */
	public static String append(final String... paths) {
		Check.notNull((Object) paths);

		String appended = Str.emptyOnNull(paths[0]);
		for (int i = 1; i < paths.length; i++) {
			appended = append(appended, paths[i]);
		}
		return appended;
	}

	public static String getRelativePath(String parentRaw, String child) {
		String parent = removeTrailingSlash(parentRaw);
		if (Str.isBlank(getHead(parent))) {
			return child;
		}
		if (getHead(parent).equals(getHead(child))) {
			return getRelativePath(getTail(parent), getTail(child));
		}
		return null;
	}

	public static String getHead(String url) {
		return url == null ? null : getPath(url).replaceFirst("^[/\\\\]", "").replaceFirst("[/\\\\].++", "");
	}

	public static String getTail(String url) {
		String unix = unix(url);
		return unix.substring(1).contains(SLASH) ? getPath(unix).replaceFirst("^/?[^/]++/?", "") : null;
	}

	public static Optional<String> getProtocol(String url) {
		if (!url.contains(":")) {
			return Optional.empty();
		}
		String protocol = url.replaceFirst(":.*+", "").trim();
		return protocol.matches("[a-z]++") ? Optional.of(protocol) : Optional.empty();
	}

	/**
	 * concatenates the parameters into a valid HTTP GET query string.
	 *
	 * @param map
	 *            key value map for the parameters. toString() will be called on the values.
	 */
	public static String getQueryString(Map<String, ?> map) {
		return UrlParameterEncoder.encodeToString(MapTool.mapValues(map, v -> String.valueOf(v)));
	}

	private static String unix(String url) {
		return url == null ? null : url.replace('\\', '/');
	}
}