package com.eriklievaart.toolkit.lang.api.collection;

public interface IterationElement<E> {

	void accept(int index, E element);
}