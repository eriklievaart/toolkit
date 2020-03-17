package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.eriklievaart.toolkit.lang.api.AssertionException;
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
	 * Create a new Map containing keys as keys and the values are derived from applying the mapping function.
	 */
	public static <K, V> Map<K, V> toMap(Collection<K> keys, Function<K, V> function) {
		Set<K> duplicates = new HashSet<>();
		Map<K, V> map = NewCollection.mapNotNull();
		for (K key : keys) {
			AssertionException.unless(duplicates.add(key), "duplicate key %", key);
			map.put(key, function.apply(key));
		}
		return map;
	}

	/**
	 * Use a collection to generate a map.
	 *
	 * @param generator
	 *            create a map entry per item
	 * @param keys
	 *            function to create key from the generator
	 * @param values
	 *            function to create key from the generator
	 */
	public static <G, K, V> Map<K, V> toMap(Collection<G> generator, Function<G, K> keys, Function<G, V> values) {
		Set<K> duplicates = new HashSet<>();
		Map<K, V> map = NewCollection.mapNotNull();
		for (G source : generator) {
			K key = keys.apply(source);
			V value = values.apply(source);
			AssertionException.unless(duplicates.add(key), "duplicate key %", key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * Apply function to values in Map and create a new map as result;
	 */
	public static <K, V, W> Map<K, W> mapValues(Map<K, V> map, Function<V, W> function) {
		return toMap(map.keySet(), k -> function.apply(map.get(k)));
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