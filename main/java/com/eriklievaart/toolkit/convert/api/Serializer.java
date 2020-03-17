package com.eriklievaart.toolkit.convert.api;

/**
 * Interface for converting Objects to Strings.
 */
public interface Serializer<T> {

	/**
	 * Convert an object of type T to a String that can be converted back into an Object.
	 *
	 * @param instance
	 *            object to convert to String.
	 * @return a String representation of this Object
	 * @throws ConversionException
	 *             if the conversion is not allowed.
	 */
	public String toString(T instance) throws ConversionException;
}