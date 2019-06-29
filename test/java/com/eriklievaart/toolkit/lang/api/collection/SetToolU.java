package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Set;

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
}
