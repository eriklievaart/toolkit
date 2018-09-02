package com.eriklievaart.toolkit.lang.api;

import com.eriklievaart.toolkit.lang.api.concurrent.Prototype;

@Prototype
public class IdGenerator {
	private long value = 0;

	public long next() {
		return value++;
	}
}
