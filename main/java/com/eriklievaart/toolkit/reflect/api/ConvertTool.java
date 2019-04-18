package com.eriklievaart.toolkit.reflect.api;

import java.sql.Timestamp;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.FormattedException;

public class ConvertTool {

	@SuppressWarnings("unchecked")
	public static <E> E convert(Object value, Class<E> typeTo) {
		return (E) convertInternal(value, typeTo);
	}

	private static Object convertInternal(Object value, Class<?> typeTo) {
		if (PrimitiveTool.isPrimitive(typeTo)) {
			return convert(value, PrimitiveTool.wrapper(typeTo));
		}
		Class<? extends Object> actualType = value.getClass();
		if (LiteralTool.isAssignable(actualType, typeTo)) {
			return value;
		}
		if (value instanceof Number && LiteralTool.isAssignable(typeTo, Number.class)) {
			return convertNumber((Number) value, typeTo);
		}
		if (actualType == Timestamp.class && typeTo == Long.class) {
			Timestamp stamp = (Timestamp) value;
			return stamp.getTime();
		}
		throw new FormattedException("Cannot convert $ to $", actualType.getSimpleName(), typeTo.getSimpleName());
	}

	private static Number convertNumber(Number value, Class<?> typeTo) {
		Class<? extends Object> actualType = value.getClass();
		long exact = value.longValue();
		if (typeTo == Long.class) {
			return exact;
		}
		if (typeTo == Integer.class) {
			AssertionException.on(exact > Integer.MAX_VALUE, "integer overflow: $", exact);
			return value.intValue();
		}
		if (typeTo == Short.class) {
			AssertionException.on(exact > Short.MAX_VALUE, "short overflow: $", exact);
			return value.shortValue();
		}
		if (typeTo == Byte.class) {
			AssertionException.on(exact > Byte.MAX_VALUE, "byte overflow: $", exact);
			return value.byteValue();
		}
		throw new FormattedException("Cannot convert $ to $", actualType.getSimpleName(), typeTo.getSimpleName());
	}
}
