package com.eriklievaart.toolkit.lang.api.function;

public interface TryFunction<A, B, E extends Exception> {

	public B apply(A value) throws E;
}
