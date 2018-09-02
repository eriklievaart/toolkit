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
		Check.isTrue(test.contains(expected), "% does not contain %", test, expected);
	}

	public static void containsIgnoreCase(String test, String expected) {
		Check.isTrue(test.toLowerCase().contains(expected.toLowerCase()), "% does not contain %", test, expected);
	}

	public static void isEmptyString(String test) {
		Check.isTrue("".equals(test), "% is not an empty string literal", test);
	}

	public static void isEqual(String test, String expected) {
		Check.isEqual(test, expected);
	}

	public static void isBlank(String test) {
		Check.isTrue(Str.isBlank(test), "% not blank", test);
	}

	public static void notContainsIgnoreCase(String test, String notExpected) {
		Check.notNull(test, notExpected);
		Check.isFalse(test.toLowerCase().contains(notExpected.toLowerCase()), "% contains %", test, notExpected);
	}

	public static void notBlank(String test) {
		if (Str.isBlank(test)) {
			throw new AssertionException("argument is blank");
		}
	}

	public static void isIdentifier(String test) {
		Check.matches(test, "\\w++");
	}
}
