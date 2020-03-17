package com.eriklievaart.toolkit.lang.api.function;

public interface TryConsumer<T, E extends Exception> {

	public void accept(T element) throws E;
}