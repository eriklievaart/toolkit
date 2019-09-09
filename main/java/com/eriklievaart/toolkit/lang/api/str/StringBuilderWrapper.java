package com.eriklievaart.toolkit.lang.api.str;

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
	public void replaceChar(int i, char c) {
		int index = translate(i);
		builder.replace(index, index + 1, "" + c);
	}

	private int translate(int i) {
		return i >= 0 ? i : builder.length() + i;
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

	public int length() {
		return builder.length();
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}