package com.eriklievaart.toolkit.swing.api.laf;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Validator;

public class ColorValidator implements Validator {

	@Override
	public boolean isValid(String value) {
		String[] rgb = value.split("[, ]++");
		if (rgb.length != 3) {
			return false;
		}
		for (String primary : rgb) {
			if (!primary.matches("\\d{1,3}")) {
				return false;
			}
			int integer = Integer.parseInt(primary);
			if (integer > 255) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void check(String value) throws ConversionException {
		ConversionException.unless(isValid(value), "colors should be formatted as 0-255,0-255,0-255");
	}
}
