package com.eriklievaart.toolkit.lang.api.function;

public interface TryBiConsumer<A, B, E extends Exception> {

	public void accept(A a, B b) throws E;
}