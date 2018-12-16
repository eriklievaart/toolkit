package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class for working with Sets.
 * 
 * @author Erik Lievaart
 */
public class SetTool {
	private SetTool() {
	}

	/**
	 * Create an unmodifiable set containing all the unique entries in the original.
	 */
	public static <E> Set<E> unmodifiableCopy(final Collection<E> original) {
		return Collections.unmodifiableSet(new HashSet<E>(original));
	}

	/**
	 * Create a TreeSet from the specified elements.
	 */
	public static <E> TreeSet<E> treeSet(final E... elements) {
		TreeSet<E> set = NewCollection.treeSet();
		for (E element : elements) {
			set.add(element);
		}
		return set;
	}

	/**
	 * Create a Set from the specified elements.
	 */
	public static <E> Set<E> of(final E... elements) {
		Set<E> set = NewCollection.set();
		set.addAll(Arrays.asList(elements));
		return set;
	}

	/**
	 * Merge the supplied Set's into a single Set containing all unique entries.
	 */
	public static <E> Set<E> merge(final Set<E>... sets) {
		Set<E> result = NewCollection.set();
		for (Set<E> set : sets) {
			result.addAll(set);
		}
		return result;
	}

	/**
	 * Returns a new set containing the elements present in one set, but not in both.
	 */
	public static <E> Set<E> disjunction(Set<E> first, Set<E> second) {
		return merge(substract(first, second), substract(second, first));
	}

	/**
	 * Returns a new set containing the elements present in keepSet, but not in removeSet.
	 */
	public static <E> Set<E> substract(Set<E> keepSet, Set<E> removeSet) {
		Set<E> result = new HashSet<>(keepSet);
		result.removeAll(removeSet);
		return result;
	}
}
