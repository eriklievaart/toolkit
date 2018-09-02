package com.eriklievaart.toolkit.convert.api;

import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;

/**
 * Constructors create Objects from raw String values.
 * 
 * @author Erik Lievaart
 * @param <T>
 *            Generic type to construct.
 */
@ThreadSafe
public interface Constructor<T> {

	/**
	 * Construct an instance from the specified String value.
	 * 
	 * @throws ConversionException
	 *             on conversion failure
	 */
	public T constructObject(String value) throws ConversionException;

	/**
	 * Get the class literal this constructor creates instances for.
	 */
	public Class<T> getLiteral();

	/**
	 * Create a converter for this constructor.
	 */
	public Converter<T> createConverter();

	/**
	 * Create a converter for this constructor, which validates the input using the specified validator.
	 */
	public Converter<T> createConverter(final Validator validator);

}
