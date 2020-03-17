package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.FilteredNotNullList;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;

public class FilteredNotNullListU {

	@Test
	public void pushPop() {
		FilteredNotNullList<String> list = new FilteredNotNullList<>();

		list.push("first");
		list.push("second");

		Check.isEqual(list.pop(), "second");
		Check.isEqual(list.pop(), "first");
	}

	@Test(expected = AssertionException.class)
	public void policyReject() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.REJECT);
		list.add(null);
	}

	@Test
	public void policyAccept() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.ACCEPT);

		CheckCollection.isEmpty(list);
		list.add(null);
		CheckCollection.isSize(list, 1);
	}

	@Test
	public void policyFilter() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.FILTER);

		CheckCollection.isEmpty(list);
		list.add(null);
		CheckCollection.isEmpty(list);
	}

	@Test
	public void addIndexedFilter() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.FILTER);

		CheckCollection.isEmpty(list);
		list.add(0, null);
		CheckCollection.isEmpty(list);
		list.add(0, "value");
		CheckCollection.isSize(list, 1);
	}

	@Test
	public void addAll() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.FILTER);
		List<String> nulls = Arrays.asList("value", null, null);

		CheckCollection.isEmpty(list);
		list.addAll(nulls);
		CheckCollection.isSize(list, 1);
	}

	@Test(expected = AssertionException.class)
	public void removeReject() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.REJECT);
		list.remove(null);
	}

	@Test
	public void removeAccept() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.ACCEPT);

		list.add("value");
		list.add(null);

		CheckCollection.isSize(list, 2);
		list.remove(null);
		CheckCollection.isSize(list, 1);
		list.remove("value");
		CheckCollection.isEmpty(list);
	}

	@Test
	public void removeFilter() {
		FilteredNotNullList<String> list = new FilteredNotNullList<String>(NullPolicy.FILTER);

		list.add("value");
		CheckCollection.isSize(list, 1);
		list.remove(null);
		CheckCollection.isSize(list, 1);
		list.remove("value");
		CheckCollection.isEmpty(list);
	}
}