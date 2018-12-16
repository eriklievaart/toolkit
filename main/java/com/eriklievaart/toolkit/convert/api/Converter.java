package com.eriklievaart.toolkit.convert.api;

import com.eriklievaart.toolkit.convert.api.validate.AlwaysValidate;
import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;

/**
 * Convertors combine validation with construction. Any value successfully validated by the validator should be usable
 * by the Constructor.
 *
 * @author Erik Lievaart
 * @param <T>
 *            Type to convert from / to {@link String}.
 */
@ThreadSafe
public class Converter<T> {

	private final Constructor<T> constructor;
	private final Serializer serializer;
	private final Validator validator;

	/**
	 * Create a Converter that validates against all input.
	 *
	 * @param constructor
	 *            create instances using this Constructor.
	 */
	public Converter(final Constructor<T> constructor) {
		this(constructor, new AlwaysValidate());
	}

	/**
	 * Create a Converter that validates against the specified Validator.
	 *
	 * @param constructor
	 *            create instances using this Constructor.
	 * @param validator
	 *            Validates the input String.
	 */
	public Converter(final Constructor<T> constructor, final Validator validator) {
		this(constructor, validator, null);
	}

	/**
	 * Create a Converter that validates against the specified Validator and serializes objects back to String.
	 *
	 * @param constructor
	 *            create instances using this Constructor.
	 * @param validator
	 *            Validates the input String.
	 * @param serializer
	 *            Converts an instance of T back to a String representation usable by the Constructor.
	 */
	public Converter(final Constructor<T> constructor, final Validator validator, Serializer<T> serializer) {
		this.constructor = constructor;
		this.validator = validator;
		this.serializer = serializer;
	}

	/**
	 * Convert the specified String into an Object.
	 */
	public T convertToObject(final String string) throws ConversionException {
		validator.check(string);
		return constructor.constructObject(string);
	}

	/**
	 * Get the Constructor used by this Converter.
	 */
	public Constructor<T> getConstructor() {
		return constructor;
	}

	/**
	 * Get the Validator used by this Converter.
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * Get the Class literal of instances created by this Converter.
	 */
	public Class<T> getLiteral() {
		return constructor.getLiteral();
	}

	/**
	 * Convert an instance of T to a String presentation usable by the constructor.
	 *
	 * @param instance
	 *            object to create a String presentation for.
	 * @return the String representation of the object.
	 * @throws ConversionException
	 *             if serializing fails.
	 */
	public String convertToString(T instance) throws ConversionException {
		if (serializer == null) {
			throw new ConversionException("No Serializer registered for type: " + constructor.getLiteral());
		}
		return serializer.toString(instance);
	}
}
