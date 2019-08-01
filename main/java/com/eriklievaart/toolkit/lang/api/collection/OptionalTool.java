package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Optional;

public class OptionalTool {

	public <E> Optional<E> notNull(E value) {
		return value == null ? Optional.empty() : Optional.of(value);
	}
}
