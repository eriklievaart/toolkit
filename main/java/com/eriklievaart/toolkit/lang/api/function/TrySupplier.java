package com.eriklievaart.toolkit.lang.api.function;

public interface TrySupplier<T, E extends Exception> {

	public T get() throws E;
}
