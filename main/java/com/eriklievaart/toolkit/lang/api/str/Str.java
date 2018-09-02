package com.eriklievaart.toolkit.lang.api.str;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.FromCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;

public class Str {

	public static final String EMPTY = "";
	public static final String NULL = "<null>";
	private static final String QUOTE = "`";

	private Str() {
	}

	public static boolean isEmpty(final String str) {
		return str == null || str.equals(EMPTY);
	}

	public static boolean notEmpty(final String str) {
		return !isEmpty(str);
	}

	public static String defaultIfEmpty(String str, String fallback) {
		return isEmpty(str) ? fallback : str;
	}

	public static String quote(final Object obj) {
		return obj == null ? NULL : QUOTE + obj.toString() + QUOTE;
	}

	public static String sub(String format, Object... args) {
		int count = format.replaceAll("[^%$]", "").length();
		Check.isTrue(count == args.length, "Expecting $ arguments % => $", count, format, Arrays.toString(args));

		Iterator<Object> iter = FromCollection.toIterator(args);
		StringBuilder builder = new StringBuilder();

		for (char c : format.toCharArray()) {
			if (c == '%') {
				builder.append(quote(iter.next()));
				continue;
			}
			if (c == '$') {
				builder.append(iter.next());
				continue;
			}
			builder.append(c);
		}
		return builder.toString();
	}

	public static String repeat(String input, int count) {
		Check.isTrue(count >= 0);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			builder.append(input);
		}
		return builder.toString();
	}

	public static String trailing(final String str, final char separator) {
		if (str == null) {
			return EMPTY;
		}
		return str.substring(str.lastIndexOf(separator) + 1);
	}

	public static boolean notBlank(final String str) {
		return !isBlank(str);
	}

	public static boolean isBlank(final String str) {
		return str == null || isEmpty(str.trim());
	}

	public static String emptyOnNull(final String str) {
		return str == null ? EMPTY : str;
	}

	public static List<String> splitOnChar(final String str, final char splitter) {
		List<String> result = NewCollection.list(NullPolicy.REJECT);
		StringBuilder builder = new StringBuilder();

		for (char c : str.toCharArray()) {
			if (c == splitter) {
				result.add(builder.toString());
				builder.delete(0, builder.length());
				continue;
			}
			builder.append(c);
		}
		result.add(builder.toString());
		return result;
	}

	public static String addLineNumbers(String str) {
		String[] split = splitLines(str);

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			builder.append("line ");
			builder.append(i + 1);
			builder.append(": ");
			builder.append(split[i]);
			builder.append("\n");
		}
		return builder.toString();
	}

	public static String[] splitLines(String str) {
		return str.split("\r?\n|\r");
	}

	public static String joinLines(Collection<String> lines) {
		return String.join("\n", lines);
	}
}
