package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Erik Lievaart
 *
 *         Set that will deal with null values. Action to take on null values depends on the {@link NullPolicy}
 *
 * @param <E>
 *            type of the elements in the collection.
 */
public class FilteredNotNullSet<E> extends HashSet<E> {

	private final NullPolicy policy;

	public FilteredNotNullSet() {
		policy = NullPolicy.FILTER;
	}

	public FilteredNotNullSet(NullPolicy policy) {
		this.policy = policy;
	}

	public FilteredNotNullSet(Collection<? extends E> c) {
		this(c, NullPolicy.FILTER);
	}

	public FilteredNotNullSet(Collection<? extends E> c, NullPolicy policy) {
		this.policy = policy;
		for (E e : c) {
			add(e);
		}
	}

	@Override
	public boolean add(E e) {
		if (policy.accept(e)) {
			return super.add(e);
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return super.addAll(new FilteredNotNullSet<>(c, policy));
	}

	@Override
	public boolean remove(Object o) {
		if (policy.accept(o)) {
			return super.remove(o);
		}
		return false;
	}
}
