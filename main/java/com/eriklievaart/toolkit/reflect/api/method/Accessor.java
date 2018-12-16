package com.eriklievaart.toolkit.reflect.api.method;

import java.lang.reflect.Method;

/**
 * MethodWrapper for calling the accessor (getter) of a property through reflection. Invoke to call the get method.
 * 
 * @author Erik Lievaart
 */
public class Accessor extends AbstractMethodWrapper {

	private static final Object[] NO_ARGUMENTS = new Object[] {};

	Accessor(final Method method, final Object instance) {
		super(method, instance);
	}

	/**
	 * Call the accessor and return the result.
	 */
	public Object invoke() {
		return invoke(NO_ARGUMENTS);
	}
}
