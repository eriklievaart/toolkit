package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collection;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class OptionalOne<E> {

	private Collection<E> collection;

	public OptionalOne(Collection<E> collection) {
		Check.notNull(collection);
		this.collection = collection;
	}

	public static <T> OptionalOne<T> of(Collection<T> collection) {
		return new OptionalOne<>(collection);
	}

	public E get() {
		CheckCollection.isSize(collection, 1);
		return collection.iterator().next();
	}

	public boolean isSingle() {
		return collection.size() == 1;
	}

	public boolean isPlural() {
		return collection.size() > 1;
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public Collection<E> unwrap() {
		return collection;
	}

	@Override
	public String toString() {
		return Str.sub("OptionalOne[$]", isEmpty() ? "" : get().toString());
	}
}
