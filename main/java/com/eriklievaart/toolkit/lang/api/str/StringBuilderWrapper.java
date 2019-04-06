package com.eriklievaart.toolkit.lang.api.str;

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

	public StringBuilder getStringBuilder() {
		return builder;
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

	public StringBuilderWrapper clear() {
		if (builder.length() > 0) {
			builder.delete(0, builder.length());
		}
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