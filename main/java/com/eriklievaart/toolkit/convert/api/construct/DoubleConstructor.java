package com.eriklievaart.toolkit.convert.api.construct;

import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.serialize.ToStringSerializer;
import com.eriklievaart.toolkit.convert.api.validate.RegexValidator;

/**
 * Constructor for Integer's.
 *
 * @author Erik Lievaart
 */
public class DoubleConstructor extends AbstractConstructor<Double> {

	@Override
	public Double constructObject(final String str) {
		return Double.parseDouble(str);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Converter<Double> createConverter() {
		return new Converter(this, new RegexValidator("-?\\d++([.](\\d++)?)?|[.](\\d++)"), new ToStringSerializer());
	}
}