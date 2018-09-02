package com.eriklievaart.toolkit.convert.api.validate;

import java.io.File;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Validator;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class ExistingFileValidator implements Validator {

	@Override
	public boolean isValid(String value) {
		return Str.isBlank(value) ? false : new File(value).isFile();
	}

	@Override
	public void check(String value) throws ConversionException {
		ConversionException.on(!isValid(value), "% is not a file", value);
	}

}
