package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class ListToolU {

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
