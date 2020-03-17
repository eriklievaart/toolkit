package com.eriklievaart.toolkit.convert.api.construct;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.serialize.ToStringSerializer;
import com.eriklievaart.toolkit.convert.api.validate.RegexValidator;

/**
 * Constructor for Boolean's.
 *
 * @author Erik Lievaart
 */
public class BooleanConstructor extends AbstractConstructor<Boolean> {

	@Override
	public Boolean constructObject(final String str) throws ConversionException {
		ConversionException.on(str == null, "<null> cannot be converted to boolean");
		return Boolean.valueOf(str);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Converter<Boolean> createConverter() {
		return new Converter(this, new RegexValidator("true|false"), new ToStringSerializer());
	}
}