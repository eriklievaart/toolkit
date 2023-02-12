package com.eriklievaart.toolkit.lang.api.check;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class CheckCollection {

	private CheckCollection() {
	}

	public static void isEmpty(final Collection<?> match) {
		isEmpty(match, "Collection not empty\n$", match);
	}

	public static void isEmpty(final Collection<?> match, final String format, final Object... args) {
		Check.notNull(match);
		Check.isTrue(match.isEmpty(), format, args);
	}

	public static void isEmpty(Map<?, ?> map) {
		Check.notNull(map);
		Check.isTrue(map.isEmpty(), "Map not empty $", map);
	}

	public static void notEmpty(final Map<?, ?> map) {
		notEmpty(map, "The Map was empty.");
	}

	public static void notEmpty(final Map<?, ?> map, final String format, final Object... args) {
		Check.notNull(map, format, args);
		Check.isTrue(map.size() > 0, format, args);
	}

	public static <E> void notEmpty(final E[] array) {
		Check.notNull(array);
		Check.isTrue(array.length > 0, "Array does not contain any elements []");
	}

	public static void notEmpty(final Collection<?> data) {
		Check.notNull(data);
		Check.isTrue(data.size() > 0, "Collection is empty");
	}

	public static void notEmpty(final Collection<?> data, final String format, final Object... args) {
		Check.notNull(data);
		Check.isTrue(data.size() > 0, format, args);
	}

	public static void isSize(final Collection<?> c, final int size) {
		Check.notNull(c);
		Check.isTrue(c.size() == size, "Collection has $ elements, expecting $: $", c.size(), size, c);
	}

	public static <A> void isSize(final A[] a, final int size) {
		Check.notNull(a);
		Check.isTrue(a.length == size, "Array has $ elements, expecting $: $", a.length, size, Arrays.asList(a));
	}

	public static void isSize(final Collection<?> c, int size, String format, Object... args) {
		Check.notNull(c);
		Check.isTrue(c.size() == size, format, args);
	}

	public static void isSize(final Map<?, ?> map, final int size) {
		Check.notNull(map);
		Check.isTrue(map.size() == size, "Map has % elements expected: %", map.size(), size);
	}

	public static <E> void isPresent(final Collection<E> collection, final E item) {
		isPresent(collection, item, "% not in %", item, collection);
	}

	public static <E> void isPresent(final Collection<E> c, final E item, final String format, final Object... args) {
		Check.isTrue(c.contains(item), format, args);
	}

	public static <E, F> void isPresent(final Map<E, F> map, final E key) {
		isPresent(map, key, "% not in map.keys\n$", key, map.keySet());
	}

	public static <E, F> void isPresent(final Map<E, F> map, final E key, final String format, final Object... args) {
		Check.notNull(map, "Map is <null>");
		Check.isTrue(map.containsKey(key), format, args);
	}

	public static <E> void notPresent(final Collection<E> c, final E item) {
		Check.isFalse(c.contains(item), "% present in $", item, c);
	}

	public static <E> void notPresent(final Collection<E> c, final E item, final String format, final Object... args) {
		Check.isFalse(c.contains(item), format, args);
	}

	public static <K> void notPresent(final Map<K, ?> map, final K key) {
		notPresent(map, key, "% in map.keys $", key, map.keySet());
	}

	public static <K> void notPresent(final Map<K, ?> map, final K key, final String format, final Object... args) {
		Check.notNull(map, "Map is <null>");
		Check.isFalse(map.containsKey(key), format, args);
	}

	/**
	 * Compares the content of two iterators and asserts that they are equal (uses up the iterator).
	 */
	public static <E> void isEqual(final Iterator<E> actual, final Iterator<E> expected) {
		if (expected == null) {
			Check.isNull(actual, "Expected <null>, but was: %", actual);
			return;
		}
		while (expected.hasNext()) {
			E next = expected.next();
			Check.isTrue(actual.hasNext(), "Actual is empty, expected: %", next);
			Check.isEqual(actual.next(), next);
		}
		if (actual.hasNext()) {
			throw new AssertionException(Str.sub("Actual has more elements than expected: %", actual.next()));
		}
	}
}