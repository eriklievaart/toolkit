package com.eriklievaart.toolkit.swing.api.laf;

import java.awt.Font;

import com.eriklievaart.toolkit.convert.api.Constructor;
import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.Validator;

public class FontConstructor implements Constructor<Font> {

	@Override
	public Font constructObject(String value) throws ConversionException {
		return Font.decode(value);
	}

	@Override
	public Class<Font> getLiteral() {
		return Font.class;
	}

	@Override
	public Converter<Font> createConverter() {
		return new Converter<>(this);
	}

	@Override
	public Converter<Font> createConverter(Validator validator) {
		return new Converter<>(this, validator);
	}
}
