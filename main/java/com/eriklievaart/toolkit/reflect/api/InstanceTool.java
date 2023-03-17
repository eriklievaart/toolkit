package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;
import com.eriklievaart.toolkit.reflect.api.method.ProxyMethodWrapper;

/**
 * This utility class can be used to work with Objects through reflection.
 *
 * @author Erik Lievaart
 */
public class InstanceTool {
	private InstanceTool() {
	}

	/**
	 * Populate an Object's setters and or fields using the supplied properties.
	 *
	 * @param properties
	 *            Map containing properties as keys and Objects to inject as values. When a setter is available for a
	 *            property, the setter will be invoked, otherwise the value will be injected to the field.
	 */
	public static void populate(final Object instance, final Map<String, ? extends Object> properties) {
		new InstanceWrapper(instance).populate(properties);
	}

	/**
	 * Get the MethodWrapper for calling a named method on an instance with a proxy Object.
	 */
	public static ProxyMethodWrapper getProxyMethodWrapper(final String name, final Object instance) {
		return new InstanceWrapper(instance).getProxyMethodWrapper(name);
	}

	/**
	 * Get the MethodWrapper for calling a named method on an instance.
	 */
	public static MethodWrapper getMethodWrapper(final String name, final Object instance) {
		return new InstanceWrapper(instance).getMethodWrapper(name);
	}

	/**
	 * Get the MethodWrapper for calling a named method with the specified argument types on an instance.
	 */
	public static MethodWrapper getMethodWrapper(final String name, final Object instance, final String... types) {
		return new InstanceWrapper(instance).getMethodWrapper(name, types);
	}

	/**
	 * Return an InstanceWrapper for an instance.
	 */
	public static InstanceWrapper wrap(final Object instance) {
		return new InstanceWrapper(instance);
	}

	public static void injectFieldByType(Object target, Object inject) {
		Check.noneNull(target, inject);
		Field into = FieldTool.getFieldByType(target.getClass(), inject.getClass());
		wrap(target).injectField(into.getName(), inject);
	}

	public static void injectFieldByTypeIfExists(Object target, Object inject) {
		Check.noneNull(target, inject);
		Optional<Field> into = FieldTool.getFieldByTypeOptional(target.getClass(), inject.getClass());
		if (into.isPresent()) {
			wrap(target).injectField(into.get().getName(), inject);
		}
	}

	public static Object readField(Object target, Field field) {
		return InstanceTool.wrap(target).getFieldValue(field.getName());
	}

	public static Object readStaticField(Field field) {
		try {
			Check.isTrue(Modifiers.of(field).isStatic(), "% is not static");
			return field.get(null);
		} catch (Exception e) {
			throw new ReflectException("% could not be read; %", e, field, e.getMessage());
		}
	}

	public static void injectField(Object target, Field field, Object value) {
		InstanceTool.wrap(target).injectField(field.getName(), value);
	}
}