package com.eriklievaart.toolkit.reflect.api.method;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Utility class for working with PropertyDescriptors. Provides easy access to the underlying property.
 *
 * @author Erik Lievaart
 */
public class PropertyWrapper implements Comparable<PropertyWrapper> {

	private final PropertyDescriptor descriptor;
	private final Method readMethod;
	private final Method writeMethod;

	/**
	 * Constructor.
	 */
	public PropertyWrapper(final PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
		readMethod = descriptor.getReadMethod();
		writeMethod = descriptor.getWriteMethod();
	}

	PropertyDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * Get the name of the property.
	 */
	public String getName() {
		return descriptor.getName();
	}

	/**
	 * Get the type of the property.
	 */
	public Class<?> getType() {
		return descriptor.getPropertyType();
	}

	/**
	 * Does the property have a get method, that can be accessed through reflection?
	 */
	public boolean isReadable() {
		return readMethod != null;
	}

	/**
	 * Does the property have a set method, that can be accessed through reflection?
	 */
	public boolean isWritable() {
		return writeMethod != null;
	}

	/**
	 * Get the Accessor on the specified target Object.
	 */
	public Accessor getAccessor(final Object target) {
		return new Accessor(readMethod, target);
	}

	/**
	 * Get the Mutator on the specified target Object.
	 */
	public Mutator getMutator(final Object target) {
		return new Mutator(writeMethod, target);
	}

	/**
	 * Get the name of the Accessor.
	 */
	public String getAccessorName() {
		return readMethod.getName();
	}

	/**
	 * Get the name of the Mutator.
	 */
	public String getMutatorName() {
		return writeMethod.getName();
	}

	/**
	 * Sorts PropertyWrapper's alphabetically.
	 */
	@Override
	public int compareTo(final PropertyWrapper o) {
		Check.notNull(o);
		return getName().compareToIgnoreCase(o.getName());
	}

	@Override
	public String toString() {
		return "PropertyWrapper[" + descriptor.getName() + "]";
	}
}