package com.eriklievaart.toolkit.lang.api.str;

import java.util.Collection;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class StringBuilderWrapper implements CharSequence {

	private final StringBuilder builder;

	public StringBuilderWrapper() {
		builder = new StringBuilder();
	}

	public StringBuilderWrapper(CharSequence value) {
		builder = new StringBuilder(value);
	}

	public StringBuilderWrapper(StringBuilder builder) {
		this.builder = builder;
	}

	public StringBuilderWrapper(String format, Object... args) {
		builder = new StringBuilder(Str.sub(format, args));
	}

	/**
	 * Get the char at index i, use negative numbers to get indexes relative to the end of the string.
	 */
	@Override
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

	public StringBuilderWrapper replaceChar(int i, String replacement) {
		int index = translate(i);
		builder.replace(index, index + 1, replacement);
		return this;
	}

	public String deleteAt(int index, int length) {
		String delete = builder.substring(index, index + length);
		builder.delete(index, index + length);
		return delete;
	}

	public StringBuilderWrapper deleteLast(int count) {
		builder.delete(builder.length() - count, builder.length());
		return this;
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

	public StringBuilderWrapper appendIf(boolean test, Object object) {
		if (test) {
			append(object);
		}
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

	public StringBuilderWrapper appendTag(String element, Map<String, ? extends Object> attributes) {
		appendTagOpen(element, attributes);
		insert(-1, "/");
		return this;
	}

	public void appendTag(String element, Map<String, ?> attributes, String body) {
		appendTagOpen(element, attributes);
		append(body);
		appendTagClose(element);
	}

	public StringBuilderWrapper appendTagOpen(String element) {
		builder.append("<").append(element).append(">");
		return this;
	}

	public StringBuilderWrapper appendTagOpen(String element, Map<String, ? extends Object> attributes) {
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

	@Override
	public int length() {
		return builder.length();
	}

	public StringBuilderWrapper trim() {
		trimLeft();
		trimRight();
		return this;
	}

	public StringBuilderWrapper trimLeft() {
		while (!isEmpty() && Character.isWhitespace(builder.charAt(0))) {
			builder.delete(0, 1);
		}
		return this;
	}

	public StringBuilderWrapper trimRight() {
		while (!isEmpty() && Character.isWhitespace(builder.charAt(builder.length() - 1))) {
			deleteLast(1);
		}
		return this;
	}

	public String getTrimmed() {
		return toString().trim();
	}

	public String[] splitLines() {
		return Str.splitLines(builder.toString());
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new StringBuilderWrapper(builder.subSequence(start, end));
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
