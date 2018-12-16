package com.eriklievaart.toolkit.lang.api.check;

import java.lang.reflect.Array;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.Obj;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class Check {

	private Check() {
	}

	public static void isFalse(final boolean b) {
		isFalse(b, "Argument is 'true'.");
	}

	public static void isFalse(final boolean b, final String format, final Object... args) {
		notTrue(b, format, args);
	}

	public static void isTrue(final boolean b) {
		isTrue(b, "Argument is 'false'.");
	}

	public static void isTrue(final boolean b, final String format, final Object... quote) {
		notTrue(!b, format, quote);
	}

	public static void notTrue(final boolean b, final String format, final Object... quote) {
		if (b) {
			throw new AssertionException(Str.sub(format, quote));
		}
	}

	public static void isInstance(final Class<?> clazz, final Object object) {
		Check.notNull(object);
		isInstance(clazz, object, "%: % not instanceof %", object, object.getClass().getName(), clazz.getName());
	}

	public static void isInstance(final Class<?> clazz, final Object object, final String format, Object... args) {
		Check.isTrue(clazz.isInstance(object), format, args);
	}

	public static void isNull(final Object obj) {
		isNull(obj, "Object is not null: %", obj);
	}

	public static void isNull(final Object obj, final String format, final Object... args) {
		isTrue(obj == null, format, args);
	}

	public static void notNull(final Object o) {
		notNull(o, "Validating " + Str.NULL);
	}

	public static void notNull(final Object... args) {
		for (int i = 0; i < args.length; i++) {
			notNull(args[i], "Argument % is <null>", i);
		}
	}

	public static void notNull(final Object o, final String format, final Object... args) {
		Check.notTrue(o == null, format, args);
	}

	public static void matches(final String value, final String regex) {
		matches(value, regex, "value % does not match regex %", value, regex);
	}

	public static void matches(final String value, final String regex, final String format, final Object... args) {
		Check.notNull(value);
		Check.isTrue(PatternTool.matches(regex, value), format, args);
	}

	public static void notBlank(final String str) {
		notBlank(str, "Argument is blank");
	}

	public static void notBlank(final String str, final String message, Object... args) {
		if (args.length == 0) {
			Check.isTrue(Str.notBlank(str), message);
		} else {
			Check.isTrue(Str.notBlank(str), message, args);
		}
	}

	/**
	 * Assert that two arguments are equal by means of Object.equal().
	 */
	public static <E> void isEqual(final E actual, final E expected) {
		if (expected == null) {
			Check.isNull(actual, "Expected <null>, but was: %", actual);
			return;
		}
		isEqual(actual, expected, "actual % != expected %", actual, expected);
	}

	public static <E> void isEqual(final E actual, final E expected, final String format, final Object... args) {
		if (expected == null) {
			Check.isNull(actual, format, args);
			return;
		}
		if (!expected.getClass().isArray()) {
			Check.isTrue(Obj.equals(actual, expected), format, args);
			return;
		}
		Check.isTrue(actual.getClass().isArray(), "Expected %, but result was not an array: %", expected, actual);

		int expectedLength = Array.getLength(expected);
		int actualLength = Array.getLength(actual);
		Check.isEqual(actualLength, expectedLength, "Expected array length %, but was %", expectedLength, actualLength);
		for (int i = 0; i < expectedLength; i++) {
			Check.isEqual(Array.get(actual, i), Array.get(expected, i));
		}
	}
}
