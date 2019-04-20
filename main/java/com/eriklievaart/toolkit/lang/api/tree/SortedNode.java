package com.eriklievaart.toolkit.lang.api.tree;

import java.util.concurrent.atomic.AtomicReference;

import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class SortedNode<E extends Comparable<E>> extends AbstractNode<E> implements Comparable<SortedNode<E>> {

	private AtomicReference<E> contents = new AtomicReference<>();

	public SortedNode(E value) {
		setValue(value);
	}

	public E getValue() {
		return contents.get();
	}

	public void setValue(E newValue) {
		Check.notNull(newValue);
		contents.set(newValue);
	}

	@Override
	public int compareTo(SortedNode<E> other) {
		return getValue().compareTo(other.getValue());
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$]", contents.get());
	}
}
