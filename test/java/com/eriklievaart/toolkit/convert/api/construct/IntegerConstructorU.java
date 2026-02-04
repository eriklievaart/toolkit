package com.eriklievaart.toolkit.convert.api.construct;

import org.junit.Test;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.mock.BombSquad;

public class IntegerConstructorU {

	@Test
	public void createConverter() {
		Converter<Integer> converter = new IntegerConstructor().createConverter();

		Check.isEqual(converter.convertToObject("1"), 1);
		Check.isEqual(converter.convertToObject("-1"), -1);

		BombSquad.diffuse("`1.`", () -> {
			Check.isEqual(converter.convertToObject("1."), 1);
		});
		BombSquad.diffuse("`1.0`", () -> {
			Check.isEqual(converter.convertToObject("1.0"), 1);
		});
		BombSquad.diffuse("`0.1`", () -> {
			Check.isEqual(converter.convertToObject("0.1"), 1);
		});
		BombSquad.diffuse("`.1`", () -> {
			Check.isEqual(converter.convertToObject(".1"), 1);
		});
		BombSquad.diffuse("`1e14`", () -> {
			Check.isEqual(converter.convertToObject("1e14"), 1);
		});
	}

	@Test(expected = ConversionException.class)
	public void createConverterFail() {
		Converter<Double> converter = new DoubleConstructor().createConverter();
		Check.isEqual(converter.convertToObject("appel"), 1.0);
	}
}
