package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;
import com.eriklievaart.toolkit.lang.api.str.Str;

/**
 * Utility class for working with class literals reflectively.
 *
 * @author Erik Lievaart
 */
@SuppressWarnings("unchecked")
public class LiteralTool {

	private LiteralTool() {
	}

	static LiteralWrapper wrap(final String clazz) {
		return new LiteralWrapper(clazz, getLiteral(clazz));
	}

	static LiteralWrapper wrap(final Class<?> clazz) {
		return new LiteralWrapper(clazz.getName(), clazz);
	}

	/**
	 * Get the literal for the specified class.
	 */
	public static Class<?> getLiteral(final String clazz) {
		try {
			return Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			throw new ReflectException(Str.sub("Cannot create class literal for: %", clazz), e);
		}
	}

	/**
	 * Get the literals for the specified classes.
	 */
	public static Class<?>[] getLiterals(final String... clazzes) {
		Class<?>[] literals = new Class<?>[clazzes.length];
		for (int i = 0; i < clazzes.length; i++) {
			literals[i] = getLiteral(clazzes[i]);
		}
		return literals;
	}

	/**
	 * Get an instance by invoking the default constructor for the specified literal.
	 */
	public static <E> E newInstance(final String clazz) {
		Check.notNull(clazz);
		return (E) wrap(clazz).newInstance();
	}

	/**
	 * Get an instance by invoking the default constructor for the specified literal.
	 */
	public static <E> E newInstance(final Class<E> clazz) {
		Check.notNull(clazz);
		return (E) newInstance(clazz.getName());
	}

	/**
	 * Get a populated instance by invoking the default constructor for the specified literal.
	 *
	 * @see InstanceTool#populate(Object, Map)
	 */
	public static <E> E newPopulatedInstance(final String clazz, final Map<String, ?> properties) {
		return (E) newPopulatedInstance(getLiteral(clazz), properties);
	}

	/**
	 * Get a populated instance by invoking the default constructor for the specified literal.
	 *
	 * @see InstanceTool#populate(Object, Map)
	 */
	public static <E> E newPopulatedInstance(final Class<E> clazz, final Map<String, ?> properties) {
		return (E) wrap(clazz).newPopulatedInstance(properties);
	}

	/**
	 * Create a new Collection of the specified type. This is a best effort attempt that will work for most Collection
	 * interfaces or implementation classes.
	 */
	public static <E> Collection<E> newCollection(final Class<?> clazz) {
		Check.isTrue(isCollection(clazz));
		if (!clazz.isInterface()) {
			return (Collection<E>) newInstance(clazz);
		}
		if (clazz == java.util.Collection.class || clazz == java.util.List.class) {
			return NewCollection.list(NullPolicy.REJECT);
		}
		if (clazz == java.util.Set.class) {
			return NewCollection.set();
		}
		throw new ReflectException("Unknown collection type: " + clazz);
	}

	/**
	 * Convert a {@link Collection} into a {@link Collection} of another type.
	 *
	 * @param <E>
	 *            type of the elements.
	 * @param type
	 *            Convert the collection to a collection of this type.
	 * @param original
	 *            the original collection.
	 * @return the elements in the original collection copied to a new collection of the specified type.
	 */
	public static <E> Collection<E> newCollection(final Class<?> type, final Collection<E> original) {
		Collection<E> collection = newCollection(type);
		collection.addAll(original);
		return collection;
	}

	/**
	 * Flag indicating if the literal is an implementation of the {@link Collection} interface.
	 */
	public static boolean isCollection(final Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * Flag indicating if the literal is an implementation of the {@link Collection} interface.
	 */
	public static boolean isCollection(final String clazz) {
		return isCollection(getLiteral(clazz));
	}

	/**
	 * Flag indicating if an instance of the first argument can be passed when the second argument is requested.
	 */
	public static boolean isAssignable(final String from, final Class<?> to) {
		Check.notNull(from, to);

		return to.isAssignableFrom(getLiteral(from));
	}

	/**
	 * Flag indicating if an instance of the first argument can be passed when the second argument is requested.
	 */
	public static boolean isAssignable(final Class<?> from, final Class<?> to) {
		Check.notNull(from, to);

		return to.isAssignableFrom(from);
	}

	/**
	 * Get the name of the package of a literal.
	 */
	public static String getPackage(final String clazz) {
		return getPackage(getLiteral(clazz));
	}

	/**
	 * Get the name of the package of a literal.
	 */
	public static String getPackage(final Class<?> clazz) {
		return clazz.getPackage().getName();
	}

	/**
	 * Get the simple name of a literal.
	 */
	public static String getSimpleName(final String clazz) {
		return getSimpleName(getLiteral(clazz));
	}

	/**
	 * Get the simple name of a literal.
	 */
	private static String getSimpleName(final Class<?> clazz) {
		return clazz.getSimpleName();
	}

	/**
	 * Flag indicating if a literal is an abstract type.
	 */
	public static boolean isAbstract(final Class<?> clazz) {
		return Modifiers.of(clazz).isAbstract();
	}

	/**
	 * Create a proxy of the specified type that delegates to the InvocationHandler.
	 */
	public static <E> E createProxy(final Class<?> type, final InvocationHandler handler) {
		Check.notNull(type, handler);
		Check.isTrue(type.isInterface(), "Not an interface: " + type.getName());
		return (E) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class<?>[] { type }, handler);
	}
}