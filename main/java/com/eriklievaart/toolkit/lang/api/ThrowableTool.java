package com.eriklievaart.toolkit.lang.api;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class ThrowableTool {

	public static <C extends Throwable> boolean isRootCause(Throwable t, Class<C> clazz) {
		return clazz.isInstance(getRootCause(t));
	}

	public static Throwable getRootCause(Throwable t) {
		Check.notNull(t);
		return t.getCause() == null ? t : getRootCause(t.getCause());
	}

	public static String toString(Throwable t) {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		append(t, builder);
		return builder.toString();
	}

	public static void append(Throwable t, StringBuilderWrapper builder) {
		if (t == null) {
			builder.append("<null>");
			return;
		}
		builder.appendLine(t.getMessage()).appendLine();
		builder.appendLine(t.getClass().getCanonicalName());
		appendStackTraceElements(t, builder);
		appendCause(t, builder);
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