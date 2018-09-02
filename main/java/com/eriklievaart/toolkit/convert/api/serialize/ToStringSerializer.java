package com.eriklievaart.toolkit.convert.api.serialize;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Serializer;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class ToStringSerializer implements Serializer<Object> {

	@Override
	public String toString(Object instance) throws ConversionException {
		Check.notNull(instance);
		return instance.toString();
	}

}
