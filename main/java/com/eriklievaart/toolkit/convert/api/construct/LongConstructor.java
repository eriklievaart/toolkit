package com.eriklievaart.toolkit.convert.api.construct;

import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.serialize.ToStringSerializer;
import com.eriklievaart.toolkit.convert.api.validate.RegexValidator;

/**
 * Constructor for Integer's.
 *
 * @author Erik Lievaart
 */
public class LongConstructor extends AbstractConstructor<Long> {

	@Override
	public Long constructObject(final String str) {
		return Long.parseLong(str);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Converter<Long> createConverter() {
		return new Converter(this, new RegexValidator("\\d++"), new ToStringSerializer());
	}
}
