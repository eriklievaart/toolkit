package com.eriklievaart.toolkit.lang.api;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class ThrowableTool {

	public static Throwable getRootCause(Throwable t) {
		Check.notNull(t);
		return t.getCause() == null ? t : getRootCause(t.getCause());
	}

	public static String toString(Throwable t) {
		StringBuilder builder = new StringBuilder();
		append(t, builder);
		return builder.toString();
	}

	public static void append(Throwable t, StringBuilder builder) {
		if (t == null) {
			builder.append("<null>");
			return;
		}
		StringBuilderWrapper sbw = new StringBuilderWrapper(builder);
		sbw.appendLine("    ", t.getClass().getCanonicalName(), " => ", t.getMessage());
		appendStackTraceElements(t, sbw);
		appendCause(t, sbw);
	}

	private static void appendCause(Throwable t, StringBuilderWrapper sbw) {
		Throwable cause = t.getCause();
		if (cause == null) {
			return;
		}
		sbw.appendLine("    caused by ", cause.getClass().getCanonicalName(), " => ", cause.getMessage());
		appendStackTraceElements(cause, sbw);
		appendCause(cause, sbw);
	}

	private static void appendStackTraceElements(Throwable t, StringBuilderWrapper sbw) {
		for (StackTraceElement element : t.getStackTrace()) {
			sbw.appendLine("        at ", element.getClassName(), ": ", element.getLineNumber());
		}
	}
}
