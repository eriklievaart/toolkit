package com.eriklievaart.toolkit.lang.api.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Utility class for working with Lists.
 *
 * @author Erik Lievaart
 */
public class ListTool {

	private ListTool() {
	}

	public static <E> boolean equalsInAnyOrder(List<E> a, List<E> b) {
		if (a.size() != b.size()) {
			return false;
		}
		Set<Integer> bRemaining = new HashSet<>(generate(0, i -> i + 1, b.size()));

		OUTER: for (E element : a) {
			for (int i = 0; i < b.size(); i++) {
				if (bRemaining.contains(i) && Objects.equals(element, b.get(i))) {
					bRemaining.remove(i);
					continue OUTER;
				}
			}
			return false; // optimization
		}
		return bRemaining.isEmpty();
	}

	public static <E> List<E> filter(Collection<E> c, Predicate<E> predicate) {
		return c.stream().filter(predicate).collect(Collectors.toList());
	}

	public static <E> List<E> filter(E[] c, Predicate<E> predicate) {
		return filter(Arrays.asList(c), predicate);
	}

	public static <E, F> List<F> map(E[] c, Function<E, F> function) {
		return map(Arrays.asList(c), function);
	}

	public static <E, K, V> List<E> map(Map<K, V> map, BiFunction<K, V, E> function) {
		List<E> result = NewCollection.list();
		map.forEach((k, v) -> result.add(function.apply(k, v)));
		return result;
	}

	public static <E, F> List<F> map(Collection<E> c, Function<E, F> function) {
		return c.stream().map(function).collect(Collectors.toList());
	}

	public static <E, F extends Comparable<F>> List<F> mapAndSort(Collection<E> c, Function<E, F> function) {
		List<F> list = map(c, function);
		Collections.sort(list);
		return list;
	}

	/**
	 * Map every element in a collection to a Collection and merge them into a single List.
	 */
	public static <E, F> List<F> mapAndMerge(Collection<E> c, Function<E, Collection<F>> function) {
		List<F> result = NewCollection.list();
		c.forEach(e -> result.addAll(function.apply(e)));
		return result;
	}

	/**
	 * Map every element in an array to a Collection and merge them into a single List.
	 */
	public static <E, F> List<F> mapAndMerge(E[] c, Function<E, Collection<F>> function) {
		return mapAndMerge(Arrays.asList(c), function);
	}

	public static <E, F> List<F> filterAndMap(Collection<E> c, Predicate<E> predicate, Function<E, F> function) {
		return c.stream().filter(predicate).map(function).collect(Collectors.toList());
	}

	public static <E, F> List<F> filterAndMap(E[] c, Predicate<E> predicate, Function<E, F> function) {
		return filterAndMap(Arrays.asList(c), predicate, function);
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
	 * Add all of the specified elements to the specified List.
	 */
	public static <E> void addAll(final List<E> list, final E[] elements) {
		list.addAll(Arrays.asList(elements));
	}

	/**
	 * Return an unmodifiable List containing the elements in the original Collection.
	 */
	public static <E> List<E> unmodifiableCopy(final Collection<E> original) {
		return Collections.unmodifiableList(new ArrayList<>(original));
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
	 * Return a copy of a list with the elements in reverse order.
	 */
	public static <E> List<E> reversedCopy(final Collection<E> original) {
		List<E> copy = new ArrayList<>(original);
		Collections.reverse(copy);
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
	@SafeVarargs
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

	/**
	 * Iterate a list in reverse order and pass index along with the element.
	 */
	public static <E> void iterateBackwards(final List<E> list, final IterationElement<E> iterate) {
		for (int i = list.size() - 1; i >= 0; i--) {
			iterate.accept(i, list.get(i));
		}
	}

	/**
	 * Return a List containing no more than max elements from the provided list.
	 *
	 * @return the oroginal List, or a copy containing the first "max" elements.
	 */
	public static <E> List<E> limitSize(List<E> list, int max) {
		return list.size() <= max ? list : subList(list, 0, max - 1);
	}

	/**
	 * Generate the specified amount of elements using operator next, starting at seed.
	 */
	public static <E> List<E> generate(E seed, UnaryOperator<E> next, int count) {
		return Stream.iterate(seed, next).limit(count).collect(Collectors.toList());
	}

	/**
	 * Randomly select 'count' elements from data. If count is larger than the data list size, the list will simply be
	 * shuffled.
	 */
	public static <E> List<E> random(List<E> data, int count) {
		int remaining = count;
		Random random = new Random();
		List<E> out = NewCollection.list();
		List<E> copy = new ArrayList<>(data);

		while (remaining-- > 0 && copy.size() > 0) {
			out.add(copy.remove(random.nextInt(copy.size())));
		}
		return out;
	}
}
