package com.eriklievaart.toolkit.reflect.api.method;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;

/**
 * MethodWrapper that can be used to call Method's with proxy Objects. Methods passed to the constructor of this class
 * are required to take a single interface as argument, otherwise Exceptions will occur.
 *
 * @author Erik Lievaart
 */
public class ProxyMethodWrapper extends AbstractMethodWrapper {

	/**
	 * This constructor throws an AssertionException if the Method has more than one argument.
	 */
	public ProxyMethodWrapper(final Method method, final Object target) {
		super(method, target);

		Class<?>[] types = method.getParameterTypes();
		Check.isTrue(types.length == 1, "Argument count is not 1, but %", types.length);
	}

	/**
	 * Invoke the Method with a proxy Object, which is created on the fly using the InvocationHandler.
	 */
	public Object invokeWithProxy(final InvocationHandler handler) {
		return super.invoke(LiteralTool.createProxy(getSingleArgumentType(), handler));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + method.getDeclaringClass().getName() + "#" + method.getName() + "]";
	}

	/**
	 * Get the type of the single argument of the method.
	 */
	public Class<?> getSingleArgumentType() {
		return method.getParameterTypes()[0];
	}
}