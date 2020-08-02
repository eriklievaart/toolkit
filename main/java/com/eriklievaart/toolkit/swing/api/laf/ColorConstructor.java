package com.eriklievaart.toolkit.swing.api.laf;

import java.awt.Color;

import com.eriklievaart.toolkit.convert.api.Constructor;
import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.Validator;

public class ColorConstructor implements Constructor<Color> {

	@Override
	public Color constructObject(String value) throws ConversionException {
		String[] parts = value.trim().split("[, ]++");
		return new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
	}

	@Override
	public Class<Color> getLiteral() {
		return java.awt.Color.class;
	}

	@Override
	public Converter<Color> createConverter() {
		return new Converter<>(this, new ColorValidator());
	}

	@Override
	public Converter<Color> createConverter(Validator validator) {
		return createConverter();
	}
}
