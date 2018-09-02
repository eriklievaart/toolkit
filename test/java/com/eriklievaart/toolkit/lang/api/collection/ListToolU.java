package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;

public class ListToolU {

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
