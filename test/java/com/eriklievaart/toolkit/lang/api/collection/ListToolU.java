package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class ListToolU {

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
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
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
	public void subList() {
		List<String> test = Arrays.asList("1", "2", "3");
		Check.isEqual(ListTool.subList(test, 2), Arrays.asList("3"));
		Check.isEqual(ListTool.subList(test, 3), Arrays.asList());
	}

	@Test
	public void subListFromTo() {
		List<String> test = Arrays.asList("1", "2", "3");
		Check.isEqual(ListTool.subList(test, 1, 1), Arrays.asList("2"));
		Check.isEqual(ListTool.subList(test, 3, -1), Arrays.asList());
		Check.isEqual(ListTool.subList(test, 0, 2), Arrays.asList("1", "2", "3"));
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
}
