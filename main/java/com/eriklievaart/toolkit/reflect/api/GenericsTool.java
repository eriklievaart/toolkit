package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Utility class for working with generics in reflection.
 * 
 * @author Erik Lievaart
 */
public class GenericsTool {
	private GenericsTool() {
	}

	/**
	 * Flag indicating whether or not the type has the specified generic parameterizations.
	 */
	public static boolean hasGenericTypes(final Type type, final Class<?>... clazz) {
		Type[] actual = getGenericTypes(type);

		if (clazz.length != actual.length) {
			return false;
		}
		for (int i = 0; i < clazz.length; i++) {
			if (clazz[i] != actual[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns generics for a type. For instance, a List&lt;String&gt; would return an array containing the String type.
	 */
	public static Type[] getGenericTypes(final Type type) {
		ReflectException.unless(type instanceof ParameterizedType, "% is not a ParameterizedType", type);
		return ((ParameterizedType) type).getActualTypeArguments();
	}

	/**
	 * Returns a single generic for a type. For instance, a List&lt;String&gt; would return the String type.
	 * 
	 * @throws ReflectException
	 *             when the Type has more than one generic parameter.
	 */
	public static Type getGenericType(final Type type) {
		Type[] types = getGenericTypes(type);
		ReflectException.on(types.length != 1, "Expecting exactly 1 generic type, found %", Arrays.toString(types));
		return types[0];
	}

	/**
	 * Returns a flag indicating whether or not the type matches the class literal.
	 */
	public static boolean isBaseType(final Type type, final Class<?> baseType) {
		return getLiteral(type) == baseType;
	}

	/**
	 * Get the literal for a Type.
	 */
	public static Class<?> getLiteral(final Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterized = (ParameterizedType) type;
			return (Class<?>) parameterized.getRawType();
		}
		return (Class<?>) type;
	}
}
