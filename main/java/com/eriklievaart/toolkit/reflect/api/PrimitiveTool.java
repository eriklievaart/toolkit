package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Utility class for working with Java primitive types (such as int, boolean, etc). String is not considered a primitive
 * by this class.
 *
 * @author Erik Lievaart
 */
public class PrimitiveTool {

	private PrimitiveTool() {
	}

	private static List<Class<?>> listPrimitives() {
		return Arrays.asList(byte.class, boolean.class, short.class, int.class, long.class, float.class, double.class,
				char.class);
	}

	private static List<Class<?>> listWrappers() {
		return Arrays.asList(Byte.class, Boolean.class, Short.class, Integer.class, Long.class, Float.class,
				Double.class, Character.class);
	}

	/**
	 * Flag indicating whether the argument is a primitive type.
	 */
	public static boolean isPrimitive(final Type literal) {
		return listPrimitives().contains(literal);
	}

	/**
	 * Flag indicating whether the argument is a wrapper Class for a primitive type.
	 */
	public static boolean isWrapper(final Class<?> literal) {
		return listWrappers().contains(literal);
	}

	/**
	 * Return the wrapper class for a primitive type.
	 */
	public static Class<?> wrapper(final Type primitive) {
		Check.isTrue(isPrimitive(primitive), "Not a primitive: " + primitive);
		return listWrappers().get(listPrimitives().indexOf(primitive));
	}

	/**
	 * Return the primitive type for a wrapper class.
	 */
	public static Class<?> primitive(final Class<?> wrapper) {
		Check.isTrue(isWrapper(wrapper), "Not a wrapper: " + wrapper);
		return listPrimitives().get(listWrappers().indexOf(wrapper));
	}

	/**
	 * Flag indicating iff the literal is one of the following: primitive type, wrapper of primitive or String.
	 */
	public static boolean isBasicType(final Class<?> literal) {
		return isPrimitive(literal) || isWrapper(literal) || literal == String.class;
	}
}