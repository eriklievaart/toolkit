package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Map;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class ValidatingMap<K, V> {

	private final Map<K, V> delegate;
	private final String id;

	public ValidatingMap(String id, Map<K, V> map) {
		Check.notNull(map, "Map % is <null>", id);
		this.id = id;
		this.delegate = map;
	}

	public V get(K key) {
		Check.isTrue(delegate.containsKey(key), "Map % does not contain %", id, key);
		return delegate.get(key);
	}

	public boolean containsKey(K key) {
		return delegate.containsKey(key);
	}

	public Set<K> keySet() {
		return delegate.keySet();
	}

}
