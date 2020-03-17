package com.eriklievaart.toolkit.lang.api;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	private AtomicLong value;

	public IdGenerator() {
		this(0);
	}

	public IdGenerator(long start) {
		value = new AtomicLong(start - 1);
	}

	public long next() {
		return value.incrementAndGet();
	}

	public int nextInt() {
		return (int) next();
	}
}