package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

/**
 * Utility class for introspecting Field's through reflection.
 *
 * @author Erik Lievaart
 */
@SuppressWarnings("unchecked")
public class FieldTool {
	private FieldTool() {
	}

	static Map<String, Field> introspectFields(final Class<?> literal) {
		Map<String, Field> fields = NewCollection.concurrentMap();

		if (literal.getSuperclass() != Object.class) {
			fields.putAll(introspectFields(literal.getSuperclass()));
		}

		for (Field field : literal.getDeclaredFields()) {
			String name = field.getName();
			try {
				fields.put(name, field);
			} catch (Exception e) {
				throw new ReflectException(Str.sub("Unable to acess Field % on %", name, literal), e);
			}
		}
		return fields;
	}

	static Map<String, Object> introspectConstants(final Class<?> literal) {
		Map<String, Object> constants = NewCollection.concurrentMap();

		for (Field field : literal.getFields()) {
			String name = field.getName();
			try {
				Modifiers modifiers = Modifiers.of(field);
				if (modifiers.isFinal() && modifiers.isStatic()) {
					if (PrimitiveTool.isBasicType(field.getType())) {
						constants.put(name, field.get(null));
					}
				}
			} catch (Exception e) {
				throw new ReflectException(Str.sub("Unable to access Field: % on %" + name, literal), e);
			}
		}
		return constants;
	}

	/**
	 * Get the value of a named constant from a literal.
	 */
	public static <E> E getConstant(final Class<?> clazz, final String constant) {
		return (E) LiteralTool.wrap(clazz).getConstant(constant);
	}

	/**
	 * Get a Map of all the constants on a literal.
	 *
	 * @return The keys are the constant names, the values are the values of the literals.
	 */
	public static Map<String, ?> getConstants(final Class<?> clazz) {
		return introspectConstants(clazz);
	}

	/**
	 * Get a Map of all the constants on a literal.
	 *
	 * @return The keys are the constant names, the values are the values of the literals.
	 */
	public static Map<String, ?> getConstants(final String clazz) {
		return FieldTool.introspectConstants(LiteralTool.getLiteral(clazz));
	}

	/**
	 * Extract the named Field from a literal.
	 */
	public static Field getField(final String clazz, final String field) {
		return LiteralTool.wrap(clazz).getField(field);
	}

	/**
	 * Extract the named Field from a literal.
	 */
	public static Field getField(final Class<?> clazz, final String field) {
		return LiteralTool.wrap(clazz).getField(field);
	}

	/**
	 * Extract all Fields from a literal.
	 */
	public static Collection<Field> getFields(final Class<?> clazz) {
		return LiteralTool.wrap(clazz).getFields().values();
	}

	/**
	 * Extract all Fields from a literal.
	 */
	public static Collection<Field> getFields(final String clazz) {
		return LiteralTool.wrap(clazz).getFields().values();
	}

	public static Field getFieldByType(String parent, Class<?> fieldType) {
		return getFieldByType(LiteralTool.getLiteral(parent), fieldType);
	}

	public static Field getFieldByType(Class<?> parent, Class<?> fieldType) {
		Optional<Field> optional = getFieldByTypeOptional(parent, fieldType);
		ReflectException.unless(optional.isPresent(), "No Fields of % in %", fieldType, parent);
		return optional.get();
	}

	public static Optional<Field> getFieldByTypeOptional(Class<?> parent, Class<?> fieldType) {
		Check.notNull(parent, fieldType);

		Field into = null;
		for (Field field : FieldTool.getFields(parent)) {
			if (LiteralTool.isAssignable(fieldType, field.getType())) {
				ReflectException.on(into != null, "Can assign % to multiple fields: % %", fieldType, into, field);
				into = field;
			}
		}
		return into == null ? Optional.empty() : Optional.of(into);
	}

	public static Class<?> getGenericLiteral(Field field) {
		return GenericsTool.getLiteral(GenericsTool.getGenericType(field.getGenericType()));
	}

	public static List<Class<?>> getGenericLiterals(Field field) {
		List<Class<?>> result = NewCollection.list();
		for (Type type : GenericsTool.getGenericTypes(field.getGenericType())) {
			result.add(GenericsTool.getLiteral(type));
		}
		return result;
	}

	public static Set<String> getFieldNames(Class<?> literal) {
		return introspectFields(literal).keySet();
	}
}