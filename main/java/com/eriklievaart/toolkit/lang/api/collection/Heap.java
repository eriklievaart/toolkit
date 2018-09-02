package com.eriklievaart.toolkit.lang.api.collection;

import java.util.List;

public interface Heap<E> extends List<E> {

	public E pop();

	public void push(E element);
}
