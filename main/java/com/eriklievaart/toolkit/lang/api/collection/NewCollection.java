package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Utility class for creating new Collection's.
 *
 * @author Erik Lievaart
 */
public class NewCollection {
	private NewCollection() {
	}

	/**
	 * Create a HashMap.
	 */
	public static <E, F> Map<E, F> hashMap() {
		return new HashMap<E, F>();
	}

	/**
	 * Create a HashTable. Non null values.
	 */
	public static <E, F> Hashtable<E, F> hashtable() {
		return new Hashtable<E, F>();
	}

	/**
	 * Returns a Map of an unspecified implementation.
	 */
	public static <E, F> Map<E, F> map() {
		return hashtable();
	}

	/**
	 * Returns a Map of an unspecified implementation, Keys and values are NOT NULL.
	 */
	public static <E, F> Map<E, F> mapNotNull() {
		return hashtable();
	}

	/**
	 * Returns a Map of an unspecified implementation, Keys and values are NOT NULL.
	 */
	public static <E, F> Map<E, F> mapNullable() {
		return new HashMap<E, F>();
	}

	/**
	 * Create a {@link TreeMap}.
	 */
	public static <E, F> TreeMap<E, F> treeMap() {
		return new TreeMap<E, F>();
	}

	/**
	 * Create a MultiMap.
	 */
	public static <E, F> MultiMap<E, F> multiMap() {
		return new MultiMap<E, F>();
	}

	/**
	 * Create a List that does not accept null values.
	 */
	public static <E> List<E> list() {
		return list(NullPolicy.REJECT);
	}

	/**
	 * Create a List of an unspecified implementation.
	 */
	public static <E> List<E> list(NullPolicy policy) {
		return new FilteredNotNullList<E>(policy);
	}

	/**
	 * Create a Concurrent Map of an unspecified implementation.
	 */
	public static <E, F> ConcurrentMap<E, F> concurrentMap() {
		return concurrentHashMap();
	}

	/**
	 * Create a {@link ConcurrentHashMap}.
	 */
	public static <E, F> ConcurrentHashMap<E, F> concurrentHashMap() {
		return new ConcurrentHashMap<E, F>();
	}

	/**
	 * Create a {@link Stack}.
	 */
	public static <E> Stack<E> stack() {
		return new Stack<E>();
	}

	/**
	 * Create a {@link TreeSet}.
	 */
	public static <E> TreeSet<E> treeSet() {
		return new TreeSet<E>();
	}

	/**
	 * Create a {@link TreeSet} with the specified {@link Comparator} for ordering.
	 */
	public static <E> TreeSet<E> treeSet(final Comparator<E> comparator) {
		return new TreeSet<E>(comparator);
	}

	/**
	 * Create a Set of an unspecified implementation. The set will not accept null values.
	 */
	public static <E> Set<E> set() {
		return new FilteredNotNullSet<E>(NullPolicy.REJECT);
	}

	/**
	 * Create a Set with the specified NullPolicyType.
	 */
	public static <E> Set<E> set(NullPolicy nill) {
		return new FilteredNotNullSet<E>(nill);
	}

	/**
	 * Create a weak reference {@link Map} of an unspecified implementation.
	 */
	public static <K, V> Map<K, V> weakMap() {
		return new WeakHashMap<K, V>();
	}

	public static <K, V> Map<K, V> orderedMap() {
		return new LinkedHashMap<K, V>();
	}

	public static <E> Set<E> concurrentSet() {
		return new CopyOnWriteArraySet<E>();
	}

	public static <E> List<E> concurrentList() {
		return new CopyOnWriteArrayList<E>();
	}
}
