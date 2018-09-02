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

	public void append(char c) {
		builder.append(c);
	}

	public void append(Object object) {
		builder.append(object == null ? "<null>" : object.toString());
	}

	public void appendLine() {
		builder.append("\n");
	}

	public void appendLine(Object... args) {
		for (int i = 0; i < args.length; i++) {
			builder.append(args[i]);
		}
		appendLine();
	}

	public void clear() {
		if (builder.length() > 0) {
			builder.delete(0, builder.length());
		}
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
