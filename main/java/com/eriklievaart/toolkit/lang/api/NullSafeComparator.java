package com.eriklievaart.toolkit.lang.api;

import java.util.Comparator;

public class NullSafeComparator<E> implements Comparator<E> {

	private Comparator<E> delegate;

	public NullSafeComparator(Comparator<E> delegate) {
		this.delegate = delegate;
	}

	@Override
	public int compare(E o1, E o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}
		if (o1 == null) {
			return Integer.MIN_VALUE;
		}
		if (o2 == null) {
			return Integer.MAX_VALUE;
		}
		return delegate.compare(o1, o2);
	}
}
