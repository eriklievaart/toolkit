package com.eriklievaart.toolkit.convert.api.validate;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Validator;

public class EmailValidator implements Validator {

	@Override
	public boolean isValid(String value) {
		return value.matches("[^@++]++@.+[.][^.]++");
	}

	@Override
	public void check(String value) throws ConversionException {
		ConversionException.unless(isValid(value), "% is not a valid email address!", value);
	}
}
