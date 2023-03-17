package com.eriklievaart.toolkit.lang.api.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * This class is very similar to the {@link Map} class, but it allows keys to have multiple values.
 *
 * @author Erik Lievaart
 */
public class MultiMap<K, V> {

	private final Map<K, List<V>> delegate;

	/**
	 * Create a MultiMap with a default delegate concurrent Map.
	 */
	public MultiMap() {
		delegate = NewCollection.concurrentMap();
	}

	/**
	 * Create a MultiMap with the supplied delegate Map.
	 */
	public MultiMap(Map<K, List<V>> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Add a value to a key, the key is added if necessary.
	 */
	public void add(final K key, final V value) {
		List<V> values = get(key);
		values.add(value);
		delegate.put(key, values);
	}

	/**
	 * Stores a single value for a key. Just like the put method of a regular map.
	 */
	public void setSingleValue(final K key, final V value) {
		Check.noneNull(key, value);
		delegate.put(key, ListTool.of(value));
	}

	/**
	 * Add a unique value (no duplicates) to a key, the key is added if necessary. Relies on the value's equals method.
	 */
	public void addUniqueValue(final K key, final V value) {
		List<V> values = get(key);
		if (values.contains(value)) {
			return;
		}
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
		return delegate.get(key) == null ? new ArrayList<>() : delegate.get(key);
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
	 * Copies all elements in this map to a Hashtable.
	 */
	public Map<K, List<V>> toHashtable() {
		return new Hashtable<>(delegate);
	}

	/**
	 * Copies all elements in this map to a HashMap.
	 */
	public Map<K, List<V>> toHashMap() {
		return new HashMap<>(delegate);
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

	public void forEach(BiConsumer<K, List<V>> action) {
		delegate.forEach(action);
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

	/**
	 * Count the keys in this MultiMap.
	 */
	public int size() {
		return delegate.size();
	}

	public boolean remove(K key, V value) {
		if (!delegate.containsKey(key)) {
			return false;
		}
		boolean result = delegate.get(key).remove(value);
		if (delegate.get(key).isEmpty()) {
			delegate.remove(key);
		}
		return result;
	}

	public List<V> remove(K key) {
		return delegate.remove(key);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}