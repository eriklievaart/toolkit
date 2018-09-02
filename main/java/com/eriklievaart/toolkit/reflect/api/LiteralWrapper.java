package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MultiMap;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.method.Mutator;
import com.eriklievaart.toolkit.reflect.api.method.PropertyWrapper;

class LiteralWrapper {

	private final String qualifiedName;
	private final Class<?> literal;
	private final Map<String, PropertyWrapper> properties;
	private final Map<String, Field> fields;
	private final MultiMap<String, Method> methods;

	LiteralWrapper(final String clazz, final Class<?> literal) {
		qualifiedName = clazz;
		this.literal = literal;

		fields = Collections.unmodifiableMap(FieldTool.introspectFields(literal));
		properties = Collections.unmodifiableMap(PropertyTool.introspectProperties(literal));
		methods = MethodTool.getMethodMap(literal);
	}

	Map<String, Field> getFields() {
		return fields;
	}

	Map<String, Field> getFields(Modifiers exclude) {
		Map<String, Field> copy = new Hashtable<>();

		Set<Entry<String, Field>> entries = fields.entrySet();
		for (Entry<String, Field> entry : entries) {
			if (exclude.containsNone(Modifiers.of(entry.getValue()))) {
				copy.put(entry.getKey(), entry.getValue());
			}
		}
		return fields;
	}

	Collection<Method> getAllMethods() {
		return methods.values();
	}

	String getQualifiedName() {
		return qualifiedName;
	}

	Class<?> getLiteral() {
		return literal;
	}

	boolean hasField(final String name) {
		return fields.containsKey(name);
	}

	Field getField(final String name) {
		ReflectException.unless(hasField(name), "Could not find field % on %", name, literal);
		return fields.get(name);
	}

	Method getMethod(final String name) {
		List<Method> matches = methods.get(name);
		ReflectException.on(matches.size() != 1, "Could not find unique method % on %", name, literal);
		return matches.get(0);
	}

	Method getMethod(final String name, final String... types) {
		return MethodTool.getMethod(methods.get(name), name, types);
	}

	Mutator getMutator(final String property, final Object target) {
		Check.notNull(property, target);

		PropertyWrapper wrapper = properties.get(property);
		Check.notNull(wrapper, "Unable to find property % on %", property, literal);
		Check.isTrue(wrapper.isWritable(), "Property % not writable on %", property, literal);

		return wrapper.getMutator(target);
	}

	Map<String, PropertyWrapper> getProperties() {
		return properties;
	}

	PropertyWrapper getProperty(final String name) {
		return properties.get(name);
	}

	Object getConstant(final String name) {
		Field field = fields.get(name);
		if (field != null) {
			Modifiers m = Modifiers.of(field);
			if (PrimitiveTool.isBasicType(field.getType()) && m.isStatic() && m.isFinal()) {
				try {
					return field.get(null);
				} catch (Exception e) {
					throw new ReflectException("Constant % cannot be read; %", e, field, e.getMessage());
				}
			}
		}
		throw new ReflectException("Unknown public constant % on %", name, literal);
	}

	Object newInstance() {
		try {
			Constructor<?> constructor = literal.getConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();

		} catch (Exception e) {
			throw new ReflectException(Str.sub("Could not create instance for: %", literal), e);
		}
	}

	Object newPopulatedInstance(final Map<String, ? extends Object> values) {
		Object instance = newInstance();
		InstanceTool.populate(instance, values);
		return instance;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + getQualifiedName() + "}";
	}

}