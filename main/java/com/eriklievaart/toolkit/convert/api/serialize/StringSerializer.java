package com.eriklievaart.toolkit.convert.api.serialize;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Serializer;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class StringSerializer implements Serializer<String> {

	@Override
	public String toString(String instance) throws ConversionException {
		Check.notNull(instance);
		return instance;
	}
}
