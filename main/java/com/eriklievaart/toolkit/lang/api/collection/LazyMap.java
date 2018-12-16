package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Map implementation that creates elements on demand. This map uses a delegate map for storing values. When a key is
 * requested, the delegate map is requested the value. If none exists a Factory method is called to generate the value.
 * These values are produced by an implementation of the Lookup interface.
 *
 * @author Erik Lievaart
 * @param <K>
 *            type of the keys
 * @param <V>
 *            type of the values
 */
public class LazyMap<K, V> implements Map<K, V> {
	private final Map<K, V> map;
	private final Function<K, V> missing;

	/**
	 * Construct a LazyMap with the specified factory for values.
	 *
	 * @param missing
	 *            Factory method for creating values.
	 */
	public LazyMap(final Function<K, V> missing) {
		this(missing, new Hashtable<K, V>());
	}

	/**
	 * Construct a LazyMap with the specified factory and delegate. The factory is used to lazily look up values. The
	 * delegate is used to store values.
	 *
	 * @param missing
	 *            Factory method for creating values.
	 */
	public LazyMap(final Function<K, V> missing, final Map<K, V> delegate) {
		map = delegate;
		this.missing = missing;
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(final Object key) {
		V value = map.get(key);
		if (value == null) {
			value = missing.apply((K) key);
			map.put((K) key, value);
		}
		Check.notNull(key);
		return value;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(final Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public V put(final K key, final V value) {
		return map.put(key, value);
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	@Override
	public V remove(final Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}
}
