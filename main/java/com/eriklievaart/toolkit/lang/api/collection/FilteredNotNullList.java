package com.eriklievaart.toolkit.lang.api.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Erik Lievaart
 *
 *         List that will deal with null values. Action to take on null values depends on the {@link NullPolicy}
 *
 * @param <E>
 *            type of the elements in the list.
 */
public class FilteredNotNullList<E> extends ArrayList<E> implements Heap<E> {

	private final NullPolicy policy;

	public FilteredNotNullList() {
		policy = NullPolicy.FILTER;
	}

	public FilteredNotNullList(NullPolicy policy) {
		this.policy = policy;
	}

	public FilteredNotNullList(Collection<? extends E> c) {
		this(c, NullPolicy.FILTER);
	}

	public FilteredNotNullList(Collection<? extends E> c, NullPolicy policy) {
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
	public void add(int index, E e) {
		if (policy.accept(e)) {
			super.add(index, e);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return super.addAll(new FilteredNotNullList<>(c, policy));
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return super.addAll(index, new FilteredNotNullList<>(c, policy));
	}

	@Override
	public boolean remove(Object o) {
		if (policy.accept(o)) {
			return super.remove(o);
		}
		return false;
	}

	@Override
	public E pop() {
		return super.remove(super.size() - 1);
	}

	@Override
	public void push(E element) {
		add(element);
	}
}