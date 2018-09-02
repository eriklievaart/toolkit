package com.eriklievaart.toolkit.lang.api.collection;

import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.OptionalOne;
import com.eriklievaart.toolkit.test.api.BombSquad;

public class OptionalOneU {

	@Test
	public void getEmpty() throws Exception {
		List<String> list = ListTool.of();

		OptionalOne<String> single = new OptionalOne<>(list);
		Check.isTrue(single.isEmpty());
		Check.isFalse(single.isSingle());
		Check.isFalse(single.isPlural());
		BombSquad.diffuse(AssertionException.class, "0 elements", () -> {
			single.get();
		});
	}

	@Test
	public void getSingle() throws Exception {
		List<String> list = ListTool.of("one");

		OptionalOne<String> single = new OptionalOne<>(list);
		Check.isFalse(single.isEmpty());
		Check.isTrue(single.isSingle());
		Check.isFalse(single.isPlural());
		CheckStr.isEqual(single.get(), "one");
	}

	@Test
	public void getPlural() throws Exception {
		List<String> list = ListTool.of("one", "two");

		OptionalOne<String> single = new OptionalOne<>(list);
		Check.isFalse(single.isEmpty());
		Check.isFalse(single.isSingle());
		Check.isTrue(single.isPlural());
		BombSquad.diffuse(AssertionException.class, "2 elements", () -> {
			single.get();
		});
	}
}
