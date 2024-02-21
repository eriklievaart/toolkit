package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class ListToolU {

	@Test
	public void push() {
		List<String> test = NewCollection.list();
		ListTool.push("1", test);
		ListTool.push("2", test);
		ListTool.push("3", test);
		Assertions.assertThat(test).containsExactly("1", "2", "3");
	}

	@Test
	public void pop() {
		List<String> test = ListTool.of("1", "3", "5");
		Check.isEqual(ListTool.pop(test), "5");
		Check.isEqual(ListTool.pop(test), "3");
		Check.isEqual(ListTool.pop(test), "1");
	}

	@Test
	public void lookupMin() {
		List<String> test = Arrays.asList("1", "3", "5");
		Check.isEqual(ListTool.lookup(test, "0"), "1");
	}

	@Test
	public void lookupMiddle() {
		List<String> test = Arrays.asList("1", "3", "5");
		Check.isEqual(ListTool.lookup(test, "2"), "3");
	}

	@Test
	public void lookupMax() {
		List<String> test = Arrays.asList("1", "3", "5");
		Check.isNull(ListTool.lookup(test, "6"));
	}

	@Test
	public void equalsInAnyOrder() {
		Check.isTrue(ListTool.equalsInAnyOrder(Arrays.asList(4, 3, 2, 1), Arrays.asList(1, 2, 3, 4)));
		Check.isTrue(ListTool.equalsInAnyOrder(Arrays.asList(4, 3, 2, 1), Arrays.asList(4, 3, 2, 1)));

		Check.isFalse(ListTool.equalsInAnyOrder(Arrays.asList(1, 2), Arrays.asList(1)));
		Check.isFalse(ListTool.equalsInAnyOrder(Arrays.asList(1), Arrays.asList(1, 2)));
		Check.isFalse(ListTool.equalsInAnyOrder(Arrays.asList(1, 2), Arrays.asList(2, 3)));
	}

	@Test
	public void sortedCopy() {
		List<Integer> original = Arrays.asList(3, 1, 2);
		List<Integer> sorted = ListTool.sortedCopy(original);

		Assertions.assertThat(sorted).containsExactly(1, 2, 3);
		Assertions.assertThat(original).containsExactly(3, 1, 2);
	}

	@Test
	public void sortedCopyWithComparator() {
		List<Integer> original = Arrays.asList(3, 1, 2);

		List<Integer> reversed = ListTool.sortedCopy(original, (a, b) -> b - a);
		Assertions.assertThat(reversed).containsExactly(3, 2, 1);
	}

	@Test
	public void reverseCopy() {
		List<Integer> original = Arrays.asList(3, 1, 2);
		List<Integer> reversed = ListTool.reversedCopy(original);

		Assertions.assertThat(reversed).containsExactly(2, 1, 3);
		Assertions.assertThat(original).containsExactly(3, 1, 2);
	}

	@Test
	public void iterate() {
		List<String> data = Arrays.asList("zero", "one", "two");

		List<Integer> indices = NewCollection.list();
		List<String> elements = NewCollection.list();
		ListTool.iterate(data, (index, value) -> {
			indices.add(index);
			elements.add(value);
		});
		Assertions.assertThat(indices).containsExactly(0, 1, 2);
		Assertions.assertThat(elements).containsExactly("zero", "one", "two");
	}

	@Test
	public void iterateBackwards() {
		List<String> data = Arrays.asList("zero", "one", "two");

		List<Integer> indices = NewCollection.list();
		List<String> elements = NewCollection.list();
		ListTool.iterateBackwards(data, (index, value) -> {
			indices.add(index);
			elements.add(value);
		});
		Assertions.assertThat(indices).containsExactly(2, 1, 0);
		Assertions.assertThat(elements).containsExactly("two", "one", "zero");
	}

	@Test
	public void generate() {
		List<Integer> result = ListTool.generate(1, i -> i + 1, 10);
		Assertions.assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

	@Test
	public void random1() {
		List<Integer> input = Arrays.asList(1);
		Assertions.assertThat(ListTool.random(input, 1)).containsExactly(1);
	}

	@Test
	public void random() {
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		List<Integer> random1 = ListTool.random(data, 4);
		List<Integer> random2 = ListTool.random(data, 4);

		Assertions.assertThat(random1).isSubsetOf(data);
		Assertions.assertThat(random2).isSubsetOf(data);
		Assertions.assertThat(random1).isNotEqualTo(random2);
		CheckCollection.isSize(random1, 4);
		CheckCollection.isSize(random2, 4);
	}

	@Test
	public void randomBorderCaseTooManyRequested() {
		List<Integer> input = Arrays.asList(1);
		Assertions.assertThat(ListTool.random(input, 2)).containsExactly(1);
	}

	@Test
	public void filter() {
		List<String> test = Arrays.asList("1a", "2a", "3a", "1b", "2b", "3b");
		List<String> result = ListTool.filter(test, e -> e.matches("[12]."));
		Assertions.assertThat(result).containsExactly("1a", "2a", "1b", "2b");
	}

	@Test
	public void filterAndMap() {
		List<String> test = Arrays.asList("1", "2", "3");
		List<Integer> result = ListTool.filterAndMap(test, e -> e.matches("[12]"), s -> Integer.parseInt(s));
		Assertions.assertThat(result).containsExactly(1, 2);
	}

	@Test
	public void map() {
		List<String> test = Arrays.asList("1", "2", "3");
		List<Integer> result = ListTool.map(test, s -> Integer.parseInt(s));
		Assertions.assertThat(result).containsExactly(1, 2, 3);
	}

	@Test
	public void map2() {
		List<String> test = Arrays.asList("1", "2", "3");
		List<Integer> result = ListTool.map2(test, Integer::parseInt, e -> e + 1);
		Assertions.assertThat(result).containsExactly(2, 3, 4);
	}

	@Test
	public void mapMap() {
		Map<String, Integer> map = MapTool.of("a", 1, "b", 2);
		List<String> result = ListTool.map(map, (k, v) -> k + ":" + v);
		Assertions.assertThat(result).containsExactly("a:1", "b:2");
	}

	@Test
	public void mapAndSort() {
		List<String> test = Arrays.asList("3", "1", "2");
		List<Integer> result = ListTool.mapAndSort(test, s -> Integer.parseInt(s));
		Assertions.assertThat(result).containsExactly(1, 2, 3);
	}

	@Test
	public void mapAndMerge() {
		List<String> test = Arrays.asList("1:2", "3:4");
		List<String> result = ListTool.mapAndMerge(test, s -> Arrays.asList(s.split(":")));
		Assertions.assertThat(result).containsExactly("1", "2", "3", "4");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void merge() {
		List<Integer> first = ListTool.of(1, 2);
		List<Integer> second = ListTool.of(3, 4);
		Assertions.assertThat(ListTool.merge(first, second)).contains(1, 2, 3, 4);
	}

	@Test
	public void subList() {
		List<String> test = Arrays.asList("1", "2", "3");
		Check.isEqual(ListTool.subList(test, 2), Arrays.asList("3"));
		Check.isEqual(ListTool.subList(test, 3), Arrays.asList());
	}

	@Test
	public void subListFromTo() {
		List<String> test = Arrays.asList("1", "2", "3");
		Assertions.assertThat(ListTool.subList(test, 1, 1)).isEqualTo(Arrays.asList("2"));
		Assertions.assertThat(ListTool.subList(test, 3, -1)).isEqualTo(Arrays.asList());
		Assertions.assertThat(ListTool.subList(test, 0, 2)).isEqualTo(Arrays.asList("1", "2", "3"));
		Assertions.assertThat(ListTool.subList(test, 0, 99)).isEqualTo(Arrays.asList("1", "2", "3"));
	}

	@Test
	public void partition() {
		List<Integer> data = Arrays.asList(0, 1, 2, 3, 4, 5);
		Map<Boolean, List<Integer>> map = ListTool.partition(data, i -> i % 2 == 0);
		Assertions.assertThat(map.get(true)).containsExactly(0, 2, 4);
		Assertions.assertThat(map.get(false)).containsExactly(1, 3, 5);
	}

	@Test
	public void limitSize() {
		List<Integer> test = Arrays.asList(1, 2, 3, 4, 5);
		Assertions.assertThat(ListTool.limitSize(test, 3)).containsExactly(1, 2, 3);
	}

	@Test
	public void limitSizeBorderCaseEqual() {
		List<Integer> test = Arrays.asList(1, 2, 3);
		Assertions.assertThat(ListTool.limitSize(test, 3)).containsExactly(1, 2, 3);
	}

	@Test
	public void limitSizeBorderCaseSmaller() {
		List<Integer> test = Arrays.asList(1, 2);
		Assertions.assertThat(ListTool.limitSize(test, 3)).containsExactly(1, 2);
	}
}
