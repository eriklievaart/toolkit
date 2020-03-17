package com.eriklievaart.toolkit.lang.api.function;

public interface TryRunnable<E extends Exception> {

	public void invoke() throws E;
}