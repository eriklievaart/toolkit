package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.FilteredNotNullSet;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;

public class FilteredNotNullSetU {

	@Test(expected = AssertionException.class)
	public void policyReject() {
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.REJECT);
		list.add(null);
	}

	@Test
	public void policyAccept() {
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.ACCEPT);

		CheckCollection.isEmpty(list);
		list.add(null);
		CheckCollection.isSize(list, 1);
	}

	@Test
	public void policyFilter() {
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.FILTER);

		CheckCollection.isEmpty(list);
		list.add(null);
		CheckCollection.isEmpty(list);
	}

	@Test
	public void addAll() {
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.FILTER);
		List<String> nulls = Arrays.asList("value", null, null);

		CheckCollection.isEmpty(list);
		list.addAll(nulls);
		CheckCollection.isSize(list, 1);
	}

	@Test(expected = AssertionException.class)
	public void removeReject() {
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.REJECT);
		list.remove(null);
	}

	@Test
	public void removeAccept() {
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.ACCEPT);

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
		FilteredNotNullSet<String> list = new FilteredNotNullSet<String>(NullPolicy.FILTER);

		list.add("value");
		list.add(null);

		CheckCollection.isSize(list, 1);
		list.remove(null);
		CheckCollection.isSize(list, 1);
		list.remove("value");
		CheckCollection.isEmpty(list);
	}
}
