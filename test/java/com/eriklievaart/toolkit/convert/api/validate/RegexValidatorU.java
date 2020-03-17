package com.eriklievaart.toolkit.convert.api.validate;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class RegexValidatorU {

	@Test
	public void isValidSuccess() {
		Check.isTrue(new RegexValidator("\\d++").isValid("1234"));
	}

	@Test
	public void isValidFail() {
		Check.isFalse(new RegexValidator("\\d++").isValid("abcd"));
	}
}