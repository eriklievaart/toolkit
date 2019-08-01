package com.eriklievaart.toolkit.bean.api;

import java.util.Arrays;
import java.util.List;

import com.eriklievaart.toolkit.convert.api.Constructor;
import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.Validator;

@SuppressWarnings("rawtypes")
public class ListConstructor implements Constructor<List> {

	@Override
	public List<?> constructObject(String value) throws ConversionException {
		return Arrays.asList(value.split(","));
	}

	@Override
	public Class<List> getLiteral() {
		return List.class;
	}

	@Override
	public Converter<List> createConverter() {
		return new Converter<>(this);
	}

	@Override
	public Converter<List> createConverter(Validator validator) {
		return new Converter<>(this, validator);
	}
}
