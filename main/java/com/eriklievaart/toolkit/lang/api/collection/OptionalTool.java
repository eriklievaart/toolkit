package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Map;
import java.util.Optional;

public class OptionalTool {

	public static <E> Optional<E> notNull(E value) {
		return value == null ? Optional.empty() : Optional.of(value);
	}

	public static <K, V> Optional<V> mapValue(Map<K, V> map, K key) {
		return map.containsKey(key) ? Optional.of(map.get(key)) : Optional.empty();
	}
}