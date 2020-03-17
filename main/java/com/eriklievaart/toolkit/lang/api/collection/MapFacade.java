package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class MapFacade<K, V> implements Map<K, V> {

	private final Collection<Map<K, V>> maps;

	public MapFacade(Collection<Map<K, V>> maps) {
		CheckCollection.notEmpty(maps);
		this.maps = maps;
	}

	@SafeVarargs
	public MapFacade(Map<K, V>... maps) {
		this(ListTool.of(maps));
	}

	@Override
	public V get(Object key) {
		for (Map<K, V> map : maps) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
		}
		throw new NullPointerException(Str.sub("% not found", key));
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		for (Map<K, V> map : maps) {
			if (map.containsKey(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (Map<K, V> map : maps) {
			if (map.containsValue(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> result = new HashSet<>();
		for (Map<K, V> map : maps) {
			result.addAll(map.entrySet());
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		for (Map<K, V> map : maps) {
			if (!map.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Set<K> keySet() {
		Set<K> result = new HashSet<>();
		for (Map<K, V> map : maps) {
			result.addAll(map.keySet());
		}
		return result;
	}

	@Override
	public V put(K key, V value) {
		return maps.iterator().next().put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (K key : map.keySet()) {
			put(key, map.get(key));
		}
	}

	@Override
	public V remove(Object key) {
		V removed = maps.iterator().next().remove(key);
		Check.notNull(removed, "% not in the first Map", key);
		return removed;
	}

	@Override
	public int size() {
		int size = 0;
		for (Map<K, V> map : maps) {
			size += map.size();
		}
		return size;
	}

	@Override
	public Collection<V> values() {
		List<V> result = new FilteredNotNullList<>();
		for (Map<K, V> map : maps) {
			result.addAll(map.values());
		}
		return result;
	}

	public boolean isRemovable(Object key) {
		return maps.iterator().next().containsKey(key);
	}
}