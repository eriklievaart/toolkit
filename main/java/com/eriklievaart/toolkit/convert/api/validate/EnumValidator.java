package com.eriklievaart.toolkit.convert.api.validate;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Validator;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class EnumValidator<E extends Enum<E>> implements Validator {

	private Class<E> type;

	public EnumValidator(Class<E> type) {
		Check.notNull(type);
		this.type = type;
	}

	@Override
	public boolean isValid(String value) {
		Set<String> values = EnumSet.allOf(type).stream().map(e -> e.name().toUpperCase()).collect(Collectors.toSet());
		return values.contains(value.toUpperCase());
	}

	@Override
	public void check(String value) throws ConversionException {
		ConversionException.unless(isValid(value), "% is not a valid value for %", value, type);
	}
}