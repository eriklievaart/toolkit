package com.eriklievaart.toolkit.convert.api.construct;

import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.serialize.ToStringSerializer;
import com.eriklievaart.toolkit.convert.api.validate.RegexValidator;

/**
 * Constructor for Integer's.
 *
 * @author Erik Lievaart
 */
public class IntegerConstructor extends AbstractConstructor<Integer> {

	@Override
	public Integer constructObject(final String str) {
		return Integer.parseInt(str);
	}

	@Override
	public Converter<Integer> createConverter() {
		return new Converter(this, new RegexValidator("\\d++"), new ToStringSerializer());
	}
}
