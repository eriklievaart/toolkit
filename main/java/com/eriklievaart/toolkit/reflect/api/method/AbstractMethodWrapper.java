package com.eriklievaart.toolkit.reflect.api.method;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.Modifiers;
import com.eriklievaart.toolkit.reflect.api.ReflectException;

abstract class AbstractMethodWrapper {

	/** Method that can be invoked by this MethodWrapper. */
	protected final Method method;
	/** Target Object the Method will be invoked on. */
	protected final Object target;

	public AbstractMethodWrapper(final Method method, final Object target) {
		checkDeclaringClass(method, target);
		method.setAccessible(true);

		this.method = method;
		this.target = target;
	}

	private static void checkDeclaringClass(final Method method, final Object target) {
		Check.notNull(method);

		if (Modifiers.of(method).isStatic()) {
			return; // No need to check instance
		}
		Class<?> declare = method.getDeclaringClass();
		ReflectException.on(!declare.isInstance(target), "Target % is not an instance of %", target, declare);
	}

	/**
	 * Invoke the method with the specified arguments.
	 */
	protected Object invoke(final Object... args) {
		try {
			return method.invoke(target, convertCollections(args));
		} catch (Exception e) {
			throw new ReflectException(Str.sub("Could not invoke % for %", method, Arrays.toString(args)), e);
		}
	}

	private Object[] convertCollections(final Object[] args) {
		Class<?>[] types = getArgumentTypes();
		if (types.length == 0) {
			Check.isTrue(args == null || args.length == 0, "Arguments not empty %", args);
			return null;
		}
		Check.isEqual(args.length, types.length, "Argument count mismatch % (needed) != %", types.length, args.length);
		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			if (!LiteralTool.isCollection(type)) {
				continue;
			}
			args[i] = LiteralTool.newCollection(type, (Collection<?>) args[i]);
		}
		return args;
	}

	/**
	 * Get the type of the argument at the specified index.
	 */
	public Class<?> getArgumentType(final int index) {
		int max = getArgumentTypes().length;
		Check.isTrue(index < max, "method % has % args", method.getName(), max);
		return getArgumentTypes()[index];
	}

	/**
	 * List the types of the arguments of the Method.
	 */
	public Class<?>[] getArgumentTypes() {
		return method.getParameterTypes();
	}

	/**
	 * Get the types of the arguments of the method with generic information included.
	 */
	public Type[] getGenericArgumentTypes() {
		return method.getGenericParameterTypes();
	}

	public String getMethodName() {
		return method.getName();
	}

	public Method getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return "AbstractMethodWrapper[" + method.getDeclaringClass().getName() + "#" + method.getName() + "]";
	}

}
