package com.eriklievaart.toolkit.convert.api.construct;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.serialize.StringSerializer;
import com.eriklievaart.toolkit.convert.api.validate.AlwaysValidate;

/**
 * Constructor for constructing String's. Simply returns the value passed.
 * 
 * @author Erik Lievaart
 */
public class StringConstructor extends AbstractConstructor<String> {

	@Override
	public String constructObject(final String str) throws ConversionException {
		ConversionException.on(str == null, "<null> cannot be converted to boolean");
		return str;
	}

	@Override
	public Converter<String> createConverter() {
		return new Converter(this, new AlwaysValidate(), new StringSerializer());
	}
}
