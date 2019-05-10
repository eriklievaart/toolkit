package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collections;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.function.TryBiConsumer;

/**
 * Utility class for working with {@link java.util.Map}'s.
 *
 * @author Erik Lievaart
 */
public class MapTool {
	private MapTool() {
	}

	/**
	 * Create an writable copy containing all the elements in the original map.
	 */
	public static <E, F> Map<E, F> modifiableCopy(final Map<E, F> original) {
		Map<E, F> map = NewCollection.map();
		map.putAll(original);
		return map;
	}

	/**
	 * Create an unmodifiable copy containing all the elements in the original map.
	 */
	public static <E, F> Map<E, F> unmodifiableCopy(final Map<E, F> original) {
		return Collections.unmodifiableMap(modifiableCopy(original));
	}

	// later inputs will override earlier inputs.
	/**
	 * Create a new map containing all the entries in the two supplied arguments, keys in the second map will override
	 * keys in the first map.
	 *
	 * @param <E>
	 *            Type of the keys.
	 * @param <F>
	 *            Type of the values.
	 * @param map1
	 *            first map to copy.
	 * @param map2
	 *            second map to copy.
	 */
	public static <E, F> Map<E, F> merge(final Map<E, F> map1, final Map<E, F> map2) {
		Map<E, F> map = NewCollection.map();
		map.putAll(map1);
		map.putAll(map2);
		return map;
	}

	/**
	 * Returns a clone of the argument where all keys become values to their respective values (which become keys). so
	 * [a => 1, b => 2] will turn into [1 => a, 2 => b].
	 */
	public static <K, V> Map<V, K> reverse(final Map<K, V> original) {
		Map<V, K> map = NewCollection.map();
		for (K key : original.keySet()) {
			map.put(original.get(key), key);
		}
		return map;
	}

	/**
	 * For each method that works even if body throws Exceptions.
	 */
	public static <K, V, E extends Exception> void forEach(Map<K, V> map, TryBiConsumer<K, V, E> consumer) throws E {
		for (K key : map.keySet()) {
			consumer.accept(key, map.get(key));
		}
	}

	/**
	 * Create a modifiable map containing only the specified key and value.
	 */
	public static <K, V> Map<K, V> of(final K key, final V value) {
		Map<K, V> map = NewCollection.map();
		map.put(key, value);
		return map;
	}

	/**
	 * Create a modifiable map containing only the specified keys and values.
	 */
	public static <K, V> Map<K, V> of(final K key1, final V value1, final K key2, final V value2) {
		Map<K, V> map = of(key1, value1);
		map.put(key2, value2);
		return map;
	}
}
