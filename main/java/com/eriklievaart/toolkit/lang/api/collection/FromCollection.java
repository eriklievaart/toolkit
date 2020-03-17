package com.eriklievaart.toolkit.lang.api.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Converts collections into other types.
 *
 * @author Erik Lievaart
 */
public class FromCollection {

	private FromCollection() {
	}

	/**
	 * Place all the elements of the original collection in a List.
	 */
	public static <E> List<E> toList(final Collection<E> original) {
		return new ArrayList<>(original);
	}

	/**
	 * Place all the elements of the original enumeration in a List.
	 */
	public static <E> List<E> toList(final Enumeration<E> enumeration) {
		return Collections.list(enumeration);
	}

	/**
	 * Place all the elements of the original iterator in a List.
	 */
	public static <E> List<E> toList(final Iterator<E> iter) {
		List<E> list = NewCollection.list(NullPolicy.ACCEPT);
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}

	/**
	 * Place all the elements in a read-write List.
	 */
	@SafeVarargs
	public static <E> List<E> toList(final E... args) {
		List<E> list = NewCollection.list(NullPolicy.ACCEPT);
		for (E item : args) {
			list.add(item);
		}
		return list;
	}

	/**
	 * Create an iterator containing the arguments.
	 *
	 * @param <E>
	 *            type of the elements.
	 */
	@SafeVarargs
	public static <E> ListIterator<E> toIterator(final E... args) {
		return Arrays.asList(args).listIterator();
	}

	/**
	 * Place all the elements of the original iterator in a Set.
	 */
	public static <E> Set<E> toSet(final Collection<E> collection) {
		return new HashSet<>(collection);
	}

	/**
	 * Place all the elements of the original collection in a Enumeration.
	 */
	public static <E> Enumeration<E> toEnumeration(final Collection<E> collection) {
		return Collections.enumeration(collection);
	}
}