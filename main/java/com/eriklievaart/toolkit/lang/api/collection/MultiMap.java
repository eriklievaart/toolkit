package com.eriklievaart.toolkit.lang.api.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * This class is very similar to the {@link Map} class, but it allows keys to have multiple values.
 *
 * @author Erik Lievaart
 */
public class MultiMap<K, V> {

	private final Map<K, List<V>> delegate = NewCollection.concurrentMap();

	/**
	 * Add a value to a key, the key is added if necessary.
	 */
	public void add(final K key, final V value) {
		List<V> values = get(key);
		values.add(value);
		delegate.put(key, values);
	}

	/**
	 * Add values to a key, the key is added if necessary.
	 */
	public void addAll(final K key, final List<V> values) {
		Check.notNull(values, "Cannot add <null> to MultiMap");

		List<V> list = get(key);
		list.addAll(values);
		delegate.put(key, list);
	}

	/**
	 * Returns all the values for a specified key, or an empty List if the key is not present.
	 */
	public List<V> get(final K key) {
		return delegate.get(key) == null ? new ArrayList<V>() : delegate.get(key);
	}

	/**
	 * Returns all the keys.
	 */
	public Set<K> keySet() {
		return delegate.keySet();
	}

	/**
	 * Returns an entySet as with the {@link Map} interface.
	 */
	public Set<Entry<K, List<V>>> entrySet() {
		return delegate.entrySet();
	}

	/**
	 * Returns a clone of this MultiMap as {@link Map}, WARNING: Missing elements return NULL.
	 */
	public Map<K, List<V>> unmodifiableMap() {
		return MapTool.unmodifiableCopy(delegate);
	}

	/**
	 * Is the specified key present in the Map?
	 */
	public boolean containsKey(final K key) {
		return delegate.containsKey(key);
	}

	/**
	 * Return a collection containing all the values in the Map. Each entry in the returned Collection is a List of
	 * values.
	 */
	public Collection<V> values() {
		List<V> values = NewCollection.list(NullPolicy.REJECT);
		for (List<V> list : delegate.values()) {
			values.addAll(list);
		}
		return values;
	}

	/**
	 * Add all of the values of another {@link MultiMap} to this one, keys will be overwritten if present.
	 */
	public void putAll(final MultiMap<K, V> that) {
		if (that == null) {
			return;
		}
		this.delegate.putAll(that.delegate);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	/**
	 * Count the keys in this MultiMap.
	 */
	public int size() {
		return delegate.size();
	}

}
