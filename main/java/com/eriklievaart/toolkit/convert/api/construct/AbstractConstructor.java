package com.eriklievaart.toolkit.convert.api.construct;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.eriklievaart.toolkit.convert.api.Constructor;
import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.Validator;

/**
 * Abstract base class for creating {@link Constructor}'s.
 *
 * @author Erik Lievaart
 *
 * @param <T>
 *            generic type of the instances to construct.
 */
public abstract class AbstractConstructor<T> implements Constructor<T> {

	/** Type of the instances constructed by this Constructor. */
	protected final Class<T> literal;

	@SuppressWarnings("unchecked")
	public AbstractConstructor() {
		Type type = getClass().getGenericSuperclass();
		literal = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
	}

	@Override
	public Class<T> getLiteral() {
		return literal;
	}

	@Override
	public Converter<T> createConverter() {
		return new Converter<T>(this);
	}

	@Override
	public Converter<T> createConverter(final Validator validator) {
		return new Converter<T>(this, validator);
	}
}
