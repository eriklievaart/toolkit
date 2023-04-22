package com.eriklievaart.toolkit.lang.api.str;

import java.util.Collection;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class StringBuilderWrapper {

	private final StringBuilder builder;

	public StringBuilderWrapper() {
		builder = new StringBuilder();
	}

	public StringBuilderWrapper(String value) {
		builder = new StringBuilder(value);
	}

	public StringBuilderWrapper(StringBuilder builder) {
		this.builder = builder;
	}

	public StringBuilderWrapper(String format, Object... args) {
		builder = new StringBuilder(Str.sub(format, args));
	}

	public StringBuilder getStringBuilder() {
		return builder;
	}

	/**
	 * Get the char at index i, use negative numbers to get indexes relative to the end of the string.
	 */
	public char charAt(int i) {
		return builder.charAt(translate(i));
	}

	public int indexOf(String str) {
		return builder.indexOf(str);
	}

	/**
	 * Check if the supplied substring is present at the specified index
	 */
	public boolean substringAt(int i, String query) {
		if (builder.length() < i + query.length()) {
			return false;
		}
		for (int q = 0; q < query.length(); q++) {
			if (builder.charAt(i + q) != query.charAt(q)) {
				return false;
			}
		}
		return true;
	}

	public boolean endsWith(String tail) {
		Check.notNull(tail);

		int lengthB = builder.length();
		int lengthT = tail.length();
		int start = lengthB - lengthT;

		if (start < 0) {
			return false;
		}
		for (int i = 0; i < lengthT; i++) {
			if (builder.charAt(start + i) != tail.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Replace the char at index i, use negative numbers to use indexes relative to the end of the string.
	 */
	public StringBuilderWrapper replaceChar(int i, char c) {
		int index = translate(i);
		builder.replace(index, index + 1, "" + c);
		return this;
	}

	public String deleteAt(int index, int length) {
		String delete = builder.substring(index, index + length);
		builder.delete(index, index + length);
		return delete;
	}

	public void deleteLast(int count) {
		builder.delete(builder.length() - count, builder.length());
	}

	private int translate(int i) {
		return i >= 0 ? i : builder.length() + i;
	}

	public StringBuilderWrapper insert(int index, String value) {
		builder.insert(translate(index), value);
		return this;
	}

	public StringBuilderWrapper append(char c) {
		builder.append(c);
		return this;
	}

	public StringBuilderWrapper append(Object object) {
		builder.append(object == null ? "<null>" : object.toString());
		return this;
	}

	public StringBuilderWrapper appendLine() {
		builder.append("\n");
		return this;
	}

	public StringBuilderWrapper appendLine(Object... args) {
		for (int i = 0; i < args.length; i++) {
			builder.append(args[i]);
		}
		appendLine();
		return this;
	}

	public <E> StringBuilderWrapper appendJoined(Collection<E> collection, String joinSeparator) {
		builder.append(Str.join(collection, joinSeparator));
		return this;
	}

	public StringBuilderWrapper appendTag(String element) {
		builder.append("<").append(element).append("/>");
		return this;
	}

	public StringBuilderWrapper appendTag(String element, Object body) {
		appendTagOpen(element).append(body).appendTagClose(element);
		return this;
	}

	public StringBuilderWrapper appendTagOpen(String element) {
		builder.append("<").append(element).append(">");
		return this;
	}

	public StringBuilderWrapper appendTagOpen(String element, Map<String, String> attributes) {
		builder.append("<").append(element);
		if (attributes != null) {
			attributes.forEach((k, v) -> {
				builder.append(' ').append(k).append("=\"").append(v).append("\"");
			});
		}
		builder.append(">");
		return this;
	}

	public StringBuilderWrapper appendTagClose(String element) {
		builder.append("</").append(element).append(">");
		return this;
	}

	public StringBuilderWrapper sub(String format, Object... args) {
		builder.append(Str.sub(format, args));
		return this;
	}

	public StringBuilderWrapper subLine(String format, Object... args) {
		appendLine(Str.sub(format, args));
		return this;
	}

	public StringBuilderWrapper clear() {
		if (builder.length() > 0) {
			builder.delete(0, builder.length());
		}
		return this;
	}

	public StringBuilderWrapper reset(Object value) {
		clear();
		append(value);
		return this;
	}

	public StringBuilderWrapper resetLine(Object value) {
		clear();
		appendLine(value);
		return this;
	}

	public boolean isEmpty() {
		return builder.length() == 0;
	}

	public boolean notEmpty() {
		return builder.length() != 0;
	}

	public boolean isBlank() {
		for (int i = 0; i < length(); i++) {
			if (builder.charAt(i) != ' ' && builder.charAt(i) != '\r' && builder.charAt(i) != '\n') {
				return false;
			}
		}
		return true;
	}

	public boolean notBlank() {
		return !isBlank();
	}

	public int length() {
		return builder.length();
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
