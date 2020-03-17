package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Field;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;
import com.eriklievaart.toolkit.reflect.api.method.PropertyWrapper;
import com.eriklievaart.toolkit.reflect.api.method.ProxyMethodWrapper;

/**
 * This utility class can be used to work with a single Object through reflection. Whenever possible, use InstanceTool
 * instead.
 *
 * @author Erik Lievaart
 * @see InstanceTool
 */
public class InstanceWrapper {

	private final LiteralWrapper literal;
	private final Object instance;

	InstanceWrapper(final Object instance) {
		Check.notNull(instance);
		this.instance = instance;
		this.literal = LiteralTool.wrap(instance.getClass());
	}

	/**
	 * @see InstanceTool#populate(Object, Map)
	 */
	public void populate(final Map<String, ? extends Object> properties) {
		ReflectException.on(instance == null, "Cannot populate <null>");
		ReflectException.on(properties == null, "Cannot populate with <null>");

		properties.forEach((name, raw) -> {
			PropertyWrapper property = literal.getProperty(name);

			if (property != null && property.isWritable()) {
				Object value = ConvertTool.convert(raw, property.getType());
				property.getMutator(instance).invoke(value);
				return;
			}
			injectField(name, raw);
		});
	}

	/**
	 * @see InstanceTool#getProxyMethodWrapper(String, Object)
	 */
	public ProxyMethodWrapper getProxyMethodWrapper(final String name) {
		return new ProxyMethodWrapper(literal.getMethod(name), instance);
	}

	/**
	 * @see InstanceTool#getMethodWrapper(String, Object)
	 */
	public MethodWrapper getMethodWrapper(final String name) {
		return new MethodWrapper(literal.getMethod(name), instance);
	}

	/**
	 * @see InstanceTool#getMethodWrapper(String, Object, String...)
	 */
	public MethodWrapper getMethodWrapper(final String name, final String... types) {
		return new MethodWrapper(literal.getMethod(name, types), instance);
	}

	/**
	 * Inject a value into a field.
	 */
	public void injectField(final String name, final Object value) {
		Field field = literal.getField(name);
		try {
			field.setAccessible(true);
			field.set(instance, ConvertTool.convert(value, field.getType()));

		} catch (Exception e) {
			throw new ReflectException("Unable to inject field: " + Str.sub(name), e);
		}
	}

	public Object getFieldValue(String name) {
		Field field = literal.getField(name);
		try {
			field.setAccessible(true);
			return field.get(instance);

		} catch (Exception e) {
			throw new ReflectException("Unable to read field: " + Str.sub(name), e);
		}
	}
}