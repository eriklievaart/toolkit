package com.eriklievaart.toolkit.convert.api;

import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;

/**
 * Validates that input String's pass custom requirements.
 *
 * @author Erik Lievaart
 */
@ThreadSafe
public interface Validator {

	/**
	 * Is the input String valid?
	 */
	boolean isValid(String value);

	/**
	 * Throws a ConversionException if the input String is not valid.
	 */
	void check(String value) throws ConversionException;
}