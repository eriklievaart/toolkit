package com.eriklievaart.toolkit.lang.api.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Utility class for working with Lists.
 *
 * @author Erik Lievaart
 */
public class ListTool {

	private ListTool() {
	}

	/**
	 * Filter elements in a list.
	 */
	public static <E> List<E> filter(List<E> test, Predicate<E> predicate) {
		return test.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * Filter elements in a list and map the results.
	 */
	public static <E, F> List<F> filterAndMap(List<E> test, Predicate<E> predicate, Function<E, F> function) {
		return test.stream().filter(predicate).map(function).collect(Collectors.toList());
	}

	/**
	 * Return a new List containing the elements from the specified index.
	 *
	 * @param <E>
	 *            type of the elements.
	 * @param list
	 *            original List
	 * @param from
	 *            first index of the elements to return.
	 * @return the sublist.
	 */
	public static <E> List<E> subList(final List<E> list, final int from) {
		Check.notNull(list);
		Check.isTrue(list.size() >= from);
		return list.subList(from, list.size());
	}

	/**
	 * Return a new List containing the elements from the specified index.
	 *
	 * @param <E>
	 *            type of the elements.
	 * @param list
	 *            original List
	 * @param from
	 *            first index of the elements to return.
	 * @param to
	 *            the last index to return. If negative, the end of the list.
	 * @return the sublist.
	 */
	public static <E> List<E> subList(List<E> list, int from, int to) {
		int max = to < 0 ? list.size() - 1 : to;

		Check.notNull(list, "supplied List is null!");
		Check.isTrue(from >= 0, "from is negative! " + from);
		Check.isTrue(list.size() >= from, "from($) > list.size: $", from, list.size());
		Check.isTrue(max < list.size());

		List<E> result = NewCollection.list();
		for (int i = from; i <= max; i++) {
			result.add(list.get(i));
		}
		return result;
	}

	/**
	 * Return an unmodifiable List containing the elements in the original Collection.
	 */
	public static <E> List<E> unmodifiableCopy(final Collection<E> original) {
		return Collections.unmodifiableList(new ArrayList<>(original));
	}

	/**
	 * Add all of the specified elements to the specified List.
	 */
	public static <E> void addAll(final List<E> list, final E[] elements) {
		list.addAll(Arrays.asList(elements));
	}

	/**
	 * Return a sorted copy of a list. Uses natural ordering.
	 */
	public static <E extends Comparable<? super E>> List<E> sortedCopy(final Collection<E> original) {
		List<E> copy = new ArrayList<>(original);
		Collections.sort(copy);
		return copy;
	}

	/**
	 * Perform a binary search on a List with the specified key and return the first element equal to or greater than
	 * the key. The list must be sorted into ascending order according to the natural ordering of its elements.
	 *
	 * @param <E>
	 *            Generic type of the key, the list and the result.
	 * @return Returns: the search key, if it is contained in the list; otherwise the insertion point. The insertion
	 *         point is defined as the the first element greater than the key, or null if all elements in the list are
	 *         less than the specified key.
	 */
	public static <E extends Comparable<? super E>> E lookup(final List<E> list, final E key) {
		int index = Collections.binarySearch(list, key);
		if (-index > list.size()) {
			return null;
		}
		return index < 0 ? list.get(-index - 1) : list.get(index);
	}

	/**
	 * Create a modifiable List from the specified elements.
	 */
	public static <E> List<E> of(final E... elements) {
		return new ArrayList<>(Arrays.asList(elements));
	}

	/**
	 * Iterate a list and pass index along with the element.
	 */
	public static <E> void iterate(final List<E> list, final IterationElement<E> iterate) {
		for (int i = 0; i < list.size(); i++) {
			iterate.accept(i, list.get(i));
		}
	}
}
