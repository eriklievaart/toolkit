package com.eriklievaart.toolkit.convert.api.construct;

import org.junit.Test;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class DoubleConstructorU {

	@Test
	public void createConverter() {
		Converter<Double> converter = new DoubleConstructor().createConverter();

		Check.isEqual(converter.convertToObject("1"), 1.0);
		Check.isEqual(converter.convertToObject("1."), 1.0);
		Check.isEqual(converter.convertToObject("1.0"), 1.0);
		Check.isEqual(converter.convertToObject(".1"), 0.1);
		Check.isEqual(converter.convertToObject("-1"), -1.0);
	}

	@Test(expected = ConversionException.class)
	public void createConverterFail() {
		Converter<Double> converter = new DoubleConstructor().createConverter();
		Check.isEqual(converter.convertToObject("appel"), 1.0);
	}
}
