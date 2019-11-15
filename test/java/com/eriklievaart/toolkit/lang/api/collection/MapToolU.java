package com.eriklievaart.toolkit.lang.api.collection;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
	public void toMapCollection() {
		List<String> keys = Arrays.asList("1", "2", "3");
		Map<String, Integer> map = MapTool.toMap(keys, Integer::parseInt);
		Check.isEqual(map.get("1"), 1);
		Check.isEqual(map.get("2"), 2);
		Check.isEqual(map.get("3"), 3);
	}

	@Test
	public void toMapNoDuplicates() {
		List<String> keys = Arrays.asList("1", "1");
		BombSquad.diffuse("duplicate key `1`", () -> MapTool.toMap(keys, Integer::parseInt));
	}

	@Test
	public void toMapValues() {
		Map<String, String> map = MapTool.mapValues(MapTool.of("a", 1, "b", 2), v -> "number" + v);
		Check.isEqual(map.get("a"), "number1");
		Check.isEqual(map.get("b"), "number2");
	}

	@Test
	public void toMap2Functions() {
		List<Integer> generator = Arrays.asList(1, 2, 3);
		Map<String, Integer> map = MapTool.toMap(generator, a -> a.toString(), a -> a * a);
		Check.isEqual(map.get("1"), 1);
		Check.isEqual(map.get("2"), 4);
		Check.isEqual(map.get("3"), 9);
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