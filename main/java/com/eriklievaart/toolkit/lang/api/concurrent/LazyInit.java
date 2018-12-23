package com.eriklievaart.toolkit.lang.api.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class LazyInit<E> {

	private AtomicReference<E> reference = new AtomicReference<>();
	private Supplier<E> supplier;

	public LazyInit(Supplier<E> supplier) {
		this.supplier = supplier;
	}

	public E get() {
		E value = reference.get();
		return value == null ? create() : value;
	}

	private synchronized E create() {
		E value = reference.get();
		if (value != null) {
			return value;
		}
		E created = supplier.get();
		reference.compareAndSet(null, created);
		return reference.get();
	}
}
