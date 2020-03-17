package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.collection.FromCollection;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.MultiMap;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

/**
 * Utility class for working with Method's through reflection.
 *
 * @author Erik Lievaart
 */
public class MethodTool {
	private MethodTool() {
	}

	/**
	 * Get a Map containing all Method's of a literal with their names as keys.
	 */
	public static MultiMap<String, Method> getMethodMap(final Class<?> literal) {
		MultiMap<String, Method> methods = NewCollection.multiMap();

		for (Method method : getAllMethods(literal)) {
			methods.add(method.getName(), method);
		}
		return methods;
	}

	/**
	 * List the public methods of a literal including inherited methods.
	 */
	public static Method[] getPublicMethods(final Class<?> literal) {
		return literal.getMethods();
	}

	/**
	 * List all methods of a literal, including inherited and private Methods.
	 */
	public static Method[] getAllMethods(final Class<?> literal) {
		List<Method> declared = FromCollection.toList(literal.getDeclaredMethods());
		Class<?> parent = literal.getSuperclass();
		if (parent != null) {
			ListTool.addAll(declared, getAllMethods(parent));
		}
		return declared.toArray(new Method[] {});
	}

	/**
	 * Extract the first Method that has the given name and parameter types.
	 */
	public static Method getMethod(final List<Method> methods, final String name, final String... types) {
		Class<?>[] parameters = LiteralTool.getLiterals(types);

		for (Method method : methods) {
			if (method.getName().equals(name) && Arrays.equals(parameters, method.getParameterTypes())) {
				return method;
			}
		}
		throw new ReflectException("Unable to find method % with arguments %", name, Arrays.toString(parameters));

	}

	/**
	 * Get the named Method.
	 *
	 * @throws ReflectException
	 *             if the method name is not unique.
	 */
	public static Method getMethod(final Class<?> clazz, final String method) {
		return LiteralTool.wrap(clazz).getMethod(method);
	}

	/**
	 * Get the named Method.
	 *
	 * @throws ReflectException
	 *             if the method name is not unique.
	 */
	public static Method getMethod(final String clazz, final String method) {
		return LiteralTool.wrap(clazz).getMethod(method);
	}
}