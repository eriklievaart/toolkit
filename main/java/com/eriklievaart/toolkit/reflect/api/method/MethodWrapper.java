package com.eriklievaart.toolkit.reflect.api.method;

import java.lang.reflect.Method;

/**
 * Calls a Method through reflection.
 * 
 * @author Erik Lievaart
 */
public class MethodWrapper extends AbstractMethodWrapper {

	/**
	 * Create a MethodWrapper for calling the specified Method on the specified target Object.
	 */
	public MethodWrapper(final Method method, final Object target) {
		super(method, target);
	}

	/**
	 * Invoke the method with the specified arguments in order.
	 */
	@Override
	public Object invoke(final Object... args) {
		return super.invoke(args);
	}
}
