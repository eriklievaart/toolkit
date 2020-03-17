package com.eriklievaart.toolkit.convert.api.validate;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class EmailValidatorU {

	@Test
	public void isValidSuccess() {
		Check.isTrue(new EmailValidator().isValid("e@e.e"));
	}

	@Test
	public void isValidFail() {
		Check.isFalse(new EmailValidator().isValid("ee.e"));
	}
}