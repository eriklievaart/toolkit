package com.eriklievaart.toolkit.lang.api.check;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.mock.BombSquad;

public class CheckCollectionU {

	@Test
	public void isEmptyMapPass() {
		Map<String, String> map = NewCollection.map();
		CheckCollection.isEmpty(map);
	}

	@Test(expected = AssertionException.class)
	public void isEmptyMapFail() {
		Map<String, String> map = NewCollection.map();
		map.put("not", "empty");
		CheckCollection.isEmpty(map);
	}

	@Test
	public void notEmptyMapPass() {
		Map<String, String> map = NewCollection.map();
		map.put("not", "empty");
		CheckCollection.notEmpty(map);
	}

	@Test(expected = AssertionException.class)
	public void notEmptyMapFail() {
		Map<String, String> map = NewCollection.map();
		CheckCollection.notEmpty(map);
	}

	@Test
	public void notPresentMapWithMessagePass() {
		Map<String, String> map = NewCollection.map();
		CheckCollection.notPresent(map, "missing", "Should not throw");
	}

	@Test
	public void notPresentMapWithMessageFail() {
		Map<String, String> map = MapTool.of("present", "value");
		BombSquad.diffuse(AssertionException.class, "exception", () -> {
			CheckCollection.notPresent(map, "present", "Expecting exception");
		});
	}

	@Test
	public void notPresentMapPass() {
		Map<String, String> map = NewCollection.map();
		CheckCollection.notPresent(map, "missing");
	}

	@Test
	public void notPresentMapFail() {
		Map<String, String> map = MapTool.of("present", "value");
		BombSquad.diffuse(AssertionException.class, "present", () -> {
			CheckCollection.notPresent(map, "present");
		});
	}
}