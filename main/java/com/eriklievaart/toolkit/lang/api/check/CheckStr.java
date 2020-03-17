package com.eriklievaart.toolkit.lang.api.check;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class CheckStr {

	public static void isEmpty(final String str) {
		Check.isTrue(Str.isEmpty(str), "String is not empty.");
	}

	public static void notEmpty(final String str) {
		notEmpty(str, "String is empty.");
	}

	public static void notEmpty(final String str, final String format, final Object... args) {
		Check.notTrue(Str.isEmpty(str), format, args);
	}

	public static void contains(String test, CharSequence expected) {
		Check.notNull(test);
		Check.isTrue(test.contains(expected), "\n% not present in \n%", expected, test);
	}

	public static void containsIgnoreCase(String test, String expected) {
		Check.notNull(test);
		Check.isTrue(test.toLowerCase().contains(expected.toLowerCase()), "% not present in %", expected, test);
	}

	public static void isEmptyString(String test) {
		Check.isTrue(test == null || "".equals(test), "% is not an empty string literal", test);
	}

	public static void isEqualIgnoreCase(String test, String expected) {
		if (test == expected) {
			return;
		}
		Check.notNull(test, expected);
		AssertionException.unless(test.equalsIgnoreCase(expected), "% not equal to %", test, expected);
	}

	public static void isEqual(String test, String expected) {
		Check.isEqual(test, expected);
	}

	public static void notEqual(String test, String expected) {
		Check.notNull(test, expected);
		Check.isFalse(test.equals(expected), "Strings are equal: %", test);
	}

	public static void isBlank(String test) {
		Check.isTrue(Str.isBlank(test), "% not blank", test);
	}

	public static void notContainsIgnoreCase(String test, String notExpected) {
		Check.notNull(test, notExpected);
		Check.isFalse(test.toLowerCase().contains(notExpected.toLowerCase()), "% contains %", test, notExpected);
	}

	public static void notBlank(String test) {
		notBlank(test, "argument is blank");
	}

	public static void notBlank(String test, String format, Object... args) {
		AssertionException.on(Str.isBlank(test), format, args);
	}

	public static void isIdentifier(String test) {
		Check.matches(test, "\\w++");
	}

	public static void isLength(String test, int expected) {
		Check.isEqual(test.length(), expected, "string length $ != expected length $", test.length(), expected);
	}
}