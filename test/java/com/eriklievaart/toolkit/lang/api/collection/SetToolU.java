package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class SetToolU {

	@Test
	public void merge() {
		Set<String> set = SetTool.merge(SetTool.of("one"), SetTool.of("two"));

		CheckCollection.isPresent(set, "one");
		CheckCollection.isPresent(set, "two");
		CheckCollection.isSize(set, 2);
	}

	@Test
	public void addAll() {
		Set<String> set = SetTool.of("one", "two");
		SetTool.addAll(set, "two", "three");

		CheckCollection.isPresent(set, "one");
		CheckCollection.isPresent(set, "two");
		CheckCollection.isPresent(set, "three");
		CheckCollection.isSize(set, 3);
	}

	@Test
	public void map() {
		List<String> test = Arrays.asList("1", "2", "3");
		Set<Integer> result = SetTool.map(test, s -> Integer.parseInt(s));
		Assertions.assertThat(result).containsExactly(1, 2, 3);
	}

	@Test
	public void mapMap() {
		Map<String, Integer> map = MapTool.of("a", 1, "b", 2);
		Set<String> result = SetTool.map(map, (k, v) -> k + ":" + v);
		Assertions.assertThat(result).containsExactly("a:1", "b:2");
	}
}
