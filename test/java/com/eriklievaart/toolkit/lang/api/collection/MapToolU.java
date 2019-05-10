package com.eriklievaart.toolkit.lang.api.collection;

import java.io.IOException;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.mock.BombSquad;

public class MapToolU {

	@Test
	public void of() {
		Map<String, String> map = MapTool.of("key", "value");
		Check.isEqual(map.get("key"), "value");
	}

	@Test
	public void reverse() {
		Map<String, Integer> map = NewCollection.map();
		map.put("A", 1);
		map.put("B", 2);

		Map<Integer, String> reversed = MapTool.reverse(map);
		Assertions.assertThat(reversed).containsEntry(1, "A");
		Assertions.assertThat(reversed).containsEntry(2, "B");
	}

	@Test
	public void forEach() {
		Map<String, String> map = MapTool.of("key", "value");

		BombSquad.diffuse(IOException.class, "catch John", () -> {
			MapTool.forEach(map, (k, v) -> {
				Check.isEqual(k, "key");
				Check.isEqual(v, "value");
				throw new IOException("catch John");
			});
		});
	}
}