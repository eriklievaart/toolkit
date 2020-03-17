package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Enumeration;
import java.util.Objects;

public class EnumerationTool {

	public static <E> boolean contains(Enumeration<E> enumeration, E element) {
		while (enumeration.hasMoreElements()) {
			if (Objects.equals(enumeration.nextElement(), element)) {
				return true;
			}
		}
		return false;
	}

	@SafeVarargs
	public static <E> Enumeration<E> of(E... elements) {
		return new Enumeration<E>() {
			private int index = 0;

			@Override
			public boolean hasMoreElements() {
				return index < elements.length;
			}

			@Override
			public E nextElement() {
				return elements[index++];
			}
		};
	}
}