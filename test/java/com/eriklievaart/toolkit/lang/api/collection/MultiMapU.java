package com.eriklievaart.toolkit.lang.api.collection;

import java.util.concurrent.atomic.AtomicInteger;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;

public class MultiMapU {

	@Test
	public void forEach() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("bytes", 1);
		map.add("integers", 2);
		map.add("integers", 3);

		AtomicInteger sum = new AtomicInteger();
		map.forEach((key, list) -> list.forEach(i -> sum.addAndGet(i)));
		Check.isEqual(sum.get(), 6);
	}

	@Test
	public void addZero() {
		MultiMap<String, Integer> map = new MultiMap<>();
		CheckCollection.isEmpty(map.get("numbers"));
	}

	@Test
	public void addOne() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		Assertions.assertThat(map.get("numbers")).containsExactly(1);
	}

	@Test
	public void addTwo() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		map.add("numbers", 2);
		Assertions.assertThat(map.get("numbers")).containsExactly(1, 2);
	}

	@Test
	public void addDuplicate() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		map.add("numbers", 1);
		Assertions.assertThat(map.get("numbers")).containsExactly(1, 1);
	}

	@Test
	public void addUniqueDuplicate() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.addUniqueValue("numbers", 1);
		map.addUniqueValue("numbers", 1);
		Assertions.assertThat(map.get("numbers")).containsExactly(1);
	}

	@Test
	public void removeZero() {
		MultiMap<String, Integer> map = new MultiMap<>();
		Check.isFalse(map.remove("numbers", 1));
		CheckCollection.isEmpty(map.keySet());
	}

	@Test
	public void removeOne() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		Check.isTrue(map.remove("numbers", 1));
		CheckCollection.isEmpty(map.keySet());
	}

	@Test
	public void removeTwo() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		map.add("numbers", 2);
		Check.isTrue(map.remove("numbers", 1));
		Check.isTrue(map.remove("numbers", 2));
		CheckCollection.isEmpty(map.keySet());
	}

	@Test
	public void removeOneOfTwo() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		map.add("numbers", 2);
		Check.isTrue(map.remove("numbers", 1));
		Assertions.assertThat(map.get("numbers")).containsExactly(2);
	}

	@Test
	public void removeKey() {
		MultiMap<String, Integer> map = new MultiMap<>();
		map.add("numbers", 1);
		map.add("numbers", 2);

		Assertions.assertThat(map.remove("numbers")).containsExactly(1, 2);
		CheckCollection.isEmpty(map.keySet());
	}
}
