package com.eriklievaart.toolkit.reflect.api.method;

import java.lang.reflect.Method;

/**
 * MethodWrapper for calling the mutator (setter) of a property through reflection. Invoke to call the set method.
 * 
 * @author Erik Lievaart
 */
public class Mutator extends AbstractMethodWrapper {

	Mutator(final Method method, final Object instance) {
		super(method, instance);
	}

	/**
	 * Invoke the setter with the specified value.
	 */
	public void invoke(final Object value) {
		invoke(new Object[] { value });
	}
}
