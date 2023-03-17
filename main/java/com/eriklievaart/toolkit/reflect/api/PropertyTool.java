package com.eriklievaart.toolkit.reflect.api;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converters;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.method.PropertyWrapper;

/**
 * Utility class for working with Java bean properties.
 *
 * @author Erik Lievaart
 */
public class PropertyTool {
	private PropertyTool() {
	}

	static Map<String, PropertyWrapper> introspectProperties(final Class<?> literal) {
		Map<String, PropertyWrapper> properties = NewCollection.concurrentMap();
		try {
			for (PropertyDescriptor descriptor : Introspector.getBeanInfo(literal).getPropertyDescriptors()) {
				properties.put(descriptor.getName(), new PropertyWrapper(descriptor));
			}
		} catch (IntrospectionException e) {
			throw new ReflectException(Str.sub("Unable to get descriptors for %", literal), e);
		}
		return properties;
	}

	/**
	 * Return a Map containing all the bean properties for a literal.
	 */
	public static Map<String, PropertyWrapper> getPropertyMap(final String clazz) {
		return LiteralTool.wrap(clazz).getProperties();
	}

	/**
	 * Return a Map containing all the bean properties for a literal.
	 */
	public static Map<String, PropertyWrapper> getPropertyMap(final Class<?> clazz) {
		return LiteralTool.wrap(clazz).getProperties();
	}

	/**
	 * Return the named property for a literal.
	 */
	public static PropertyWrapper getProperty(final Class<?> clazz, final String name) {
		return LiteralTool.wrap(clazz).getProperty(name);
	}

	/**
	 * Return a Collection containing all the bean properties for a literal.
	 */
	public static Collection<PropertyWrapper> getProperties(final String clazz) {
		return LiteralTool.wrap(clazz).getProperties().values();
	}

	/**
	 * Return a Collection containing all the bean properties for a literal.
	 */
	public static Collection<PropertyWrapper> getProperties(final Class<?> clazz) {
		return LiteralTool.wrap(clazz).getProperties().values();
	}

	/**
	 * Return a Collection containing all the writable bean properties for a literal.
	 */
	public static Collection<PropertyWrapper> getWritableProperties(final Class<?> clazz) {
		return getWritableProperties(getProperties(clazz));
	}

	/**
	 * Filter the property wrappers and return only those properties which have an accessible setter.
	 */
	public static List<PropertyWrapper> getWritableProperties(final Collection<PropertyWrapper> properties) {
		List<PropertyWrapper> result = NewCollection.list(NullPolicy.REJECT);
		for (PropertyWrapper property : properties) {
			if (property.isWritable()) {
				result.add(property);
			}
		}
		return result;
	}

	/**
	 * Converts properties in a Map and injects them into the instance as properties. Only basic types are supported.
	 */
	public static void convertAndInjectProperties(Object instance, Map<String, String> properties) {
		convertAndInjectProperties(instance, properties, Converters.BASIC_CONVERTERS);
	}

	/**
	 * Converts properties in a Map and injects them into the instance as properties.
	 */
	private static void convertAndInjectProperties(Object object, Map<String, String> properties, Converters convert) {
		Check.noneNull(object, properties, convert);

		Class<? extends Object> type = object.getClass();
		Map<String, PropertyWrapper> map = getPropertyMap(type);

		Map<String, Object> convertedProperties = NewCollection.mapNotNull();
		for (String property : properties.keySet()) {
			PropertyWrapper wrapper = map.get(property);
			Check.notNull(wrapper, "property % not found on $", property, type);
			Check.isTrue(wrapper.isWritable(), "property % is not writable on $", property, type);
			try {
				convertedProperties.put(property, convert.to(wrapper.getType(), properties.get(property)));
			} catch (ConversionException e) {
				throw new ReflectException("Cannot convert property % on $", e, property, type);
			}
		}
		InstanceTool.populate(object, convertedProperties);
	}
}