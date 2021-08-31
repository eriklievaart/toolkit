package com.eriklievaart.toolkit.lang.api.check;

import java.lang.reflect.Array;
import java.util.Optional;

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
		matches(value, regex, "value %\n\tdoes not match regex %", value, regex);
	}

	public static void matches(final String value, final String regex, final String format, final Object... args) {
		Check.notNull(value);
		Check.isTrue(PatternTool.matches(regex, value), format, args);
	}

	public static void notBlank(final String str) {
		notBlank(str, "Argument is blank");
	}

	public static void notBlank(final String str, final String message, Object... args) {
		Check.isTrue(Str.notBlank(str), message, args);
	}

	public static void isDouble(double value, double expect) {
		isDouble(value, expect, 0.000000001);
	}

	public static void isDouble(double value, double expect, double precision) {
		Check.isTrue(value - precision < expect, "$ > $", value, expect);
		Check.isTrue(value + precision > expect, "$ < $", value, expect);
	}

	/**
	 * Assert that two arguments are equal by means of Object.equal().
	 */
	public static <E> void isEqual(final E actual, final E expected) {
		if (expected == null) {
			Check.isNull(actual, "Expected\n<null>, but was:\n%", actual);
			return;
		}
		isEqual(actual, expected, "actual \n% != expected \n%", actual, expected);
	}

	public static <E> void isEqual(final E actual, final E expected, final String format, final Object... args) {
		if (expected == null) {
			Check.isNull(actual, format, args);
			return;
		}
		Check.notNull(actual, "\nExpected %,\n but was <null>", expected);

		if (expected instanceof Number && actual instanceof Number) {
			isEqual((Number) actual, (Number) expected, format, args);
		}
		if (expected.getClass().isArray()) {
			Check.isTrue(actual.getClass().isArray(), "Expected %, but result was not an array: %", expected, actual);
			isArrayEqual(actual, expected);
			return;
		}
		Check.isTrue(Obj.equals(actual, expected), format, args);
	}

	public static void isEqual(Number actual, Number expected) {
		Check.isEqual(actual, expected, "actual % != expected %", actual, expected);
	}

	public static void isEqual(Number actual, Number expected, final String format, final Object... args) {
		Check.notNull(actual, expected);

		if (expected instanceof Long || expected instanceof Integer || expected instanceof Byte) {
			isLongValueEqual(actual, expected, format, args);
			return;
		}

		if (expected instanceof Float || expected instanceof Double) {
			isDoubleValueEqual(actual, expected, format, args);
			return;
		}
		Check.isTrue(actual.equals(expected), format, args);
	}

	private static void isLongValueEqual(Number actual, Number expected, final String format, final Object... args) {
		Check.isTrue(actual.longValue() == expected.longValue(), format, args);

		Class<?> actualClass = actual.getClass();
		Class<?> expectedClass = expected.getClass();
		String actualName = actualClass.getSimpleName();
		String expectedName = expectedClass.getSimpleName();

		Check.isTrue(actualClass == expectedClass, "Number type\n actual $ != expected $", actualName, expectedName);
	}

	private static void isDoubleValueEqual(Number actual, Number expected, final String format, final Object... args) {
		Check.isTrue(actual.doubleValue() == expected.doubleValue(), format, args);

		Class<?> actualClass = actual.getClass();
		Class<?> expectedClass = expected.getClass();
		String actualName = actualClass.getSimpleName();
		String expectedName = expectedClass.getSimpleName();

		Check.isTrue(actualClass == expectedClass, "Number type actual $ != expected $", actualName, expectedName);
	}

	private static <E> void isArrayEqual(final E actual, final E expected) {
		int el = Array.getLength(expected);
		int al = Array.getLength(actual);
		Check.isEqual(al, el, "Expected array length $, but was $ -> $", el, al);
		for (int i = 0; i < el; i++) {
			Object ai = Array.get(actual, i);
			Object ei = Array.get(expected, i);
			Check.isEqual(ai, ei, "value mismatch at index $ ($ != $)", i, ai, ei);
		}
	}

	public static void isPresent(Optional<?> optional) {
		Check.isTrue(optional.isPresent(), "optional is empty");
	}

	public static void isEmpty(Optional<?> optional) {
		if (optional.isPresent()) {
			throw new AssertionException("optional has value %", optional.get());
		}
	}
}