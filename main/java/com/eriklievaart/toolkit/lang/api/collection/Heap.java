package com.eriklievaart.toolkit.lang.api.collection;

import java.util.List;

public interface Heap<E> extends List<E> {

	public void push(E element);

	public E peek();

	public E pop();
}