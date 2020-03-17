package com.eriklievaart.toolkit.convert.api.validate;

import org.junit.Test;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.validate.MapValidator;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class MapValidatorU {

	@Test
	public void validateSuccess() throws ConversionException {
		new MapValidator().check("key1=value1");
		new MapValidator().check("key1=value1#key2=value2");
	}

	@Test
	public void validateEmpty() {
		Check.isFalse(new MapValidator().isValid(""));
	}

	@Test
	public void validateEmptyKeyValue() {
		Check.isFalse(new MapValidator().isValid("="));
	}

	@Test
	public void validateEmptyKey() {
		Check.isFalse(new MapValidator().isValid("=value"));
	}

	@Test
	public void validateEmptyValue() {
		Check.isFalse(new MapValidator().isValid("key1="));
	}

	@Test
	public void validateEmptyEntry() {
		Check.isFalse(new MapValidator().isValid("key1=value1#"));
	}

	@Test
	public void validateEmptyValue2() {
		Check.isFalse(new MapValidator().isValid("key1=value1#key2="));
	}
}