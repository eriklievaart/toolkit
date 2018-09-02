package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Collection;

import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class CollectionTool {

	public static <E> E getSingle(Collection<E> collection) {
		CheckCollection.isSize(collection, 1);
		return collection.iterator().next();
	}

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
}
