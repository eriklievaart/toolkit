package com.eriklievaart.toolkit.convert.api.construct;

import com.eriklievaart.toolkit.convert.api.Constructor;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.Validator;
import com.eriklievaart.toolkit.convert.api.serialize.ToStringSerializer;
import com.eriklievaart.toolkit.convert.api.validate.EnumValidator;

/**
 * Constructor for Integer's.
 *
 * @author Erik Lievaart
 */
public class EnumConstructor<E extends Enum<E>> implements Constructor<E> {

	private Class<E> literal;

	public EnumConstructor(Class<E> literal) {
		this.literal = literal;
	}

	@Override
	public Class<E> getLiteral() {
		return literal;
	}

	@Override
	public E constructObject(final String str) {
		return Enum.valueOf(literal, str.toUpperCase());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Converter<E> createConverter() {
		return new Converter(this, new EnumValidator(literal), new ToStringSerializer());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Converter<E> createConverter(Validator validator) {
		return new Converter(this, validator, new ToStringSerializer());
	}
}
