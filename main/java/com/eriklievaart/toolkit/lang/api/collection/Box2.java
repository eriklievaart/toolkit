package com.eriklievaart.toolkit.lang.api.collection;

public class Box2<A, B> {

	private final A primary;
	private final B secondary;

	public Box2(A primary, B secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}

	public A getId() {
		return primary;
	}

	public A getKey() {
		return primary;
	}

	public A getFirst() {
		return primary;
	}

	public B getSecond() {
		return secondary;
	}

	public B getValue() {
		return secondary;
	}

	public B getObject() {
		return secondary;
	}
}
