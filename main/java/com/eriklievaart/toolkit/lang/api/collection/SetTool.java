package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		return Collections.unmodifiableSet(new HashSet<>(original));
	}

	/**
	 * Create a TreeSet from the specified elements.
	 */
	@SafeVarargs
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
	@SafeVarargs
	public static <E> Set<E> of(final E... elements) {
		Set<E> set = NewCollection.set();
		set.addAll(Arrays.asList(elements));
		return set;
	}

	/**
	 * add the elements in an array to a set
	 */
	@SafeVarargs
	public static <E> void addAll(Set<E> set, E... values) {
		for (E value : values) {
			set.add(value);
		}
	}

	/**
	 * Merge the supplied Set's into a single Set containing all unique entries.
	 */
	@SafeVarargs
	public static <E> Set<E> merge(final Set<E>... sets) {
		Set<E> result = NewCollection.set();
		for (Set<E> set : sets) {
			result.addAll(set);
		}
		return result;
	}

	public static <E, F> Set<F> map(E[] c, Function<E, F> function) {
		return map(Arrays.asList(c), function);
	}

	public static <E, K, V> Set<E> map(Map<K, V> map, BiFunction<K, V, E> function) {
		Set<E> result = NewCollection.set();
		map.forEach((k, v) -> result.add(function.apply(k, v)));
		return result;
	}

	public static <E, F> Set<F> map(Collection<E> c, Function<E, F> function) {
		return c.stream().map(function).collect(Collectors.toSet());
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