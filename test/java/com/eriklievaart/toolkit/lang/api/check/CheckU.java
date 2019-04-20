package com.eriklievaart.toolkit.lang.api.check;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.mock.BombSquad;

public class CheckU {

	@Test
	public void checkIsEqualStringPass() {
		Check.isEqual("same", "same");
	}

	@Test
	public void checkIsEqualStringFail() {
		BombSquad.diffuse(AssertionException.class, "!=", () -> Check.isEqual("different1", "different2"));
	}

	@Test
	public void checkIsEqualIntegersPass() {
		Check.isEqual(1, 1);
	}

	@Test
	public void checkIsEqualIntegersFail() {
		BombSquad.diffuse(AssertionException.class, "!=", () -> Check.isEqual(1, 2));
	}

	@Test
	public void checkIsEqualIntegerWithLong() {
		String message = "actual Integer != expected Long";
		BombSquad.diffuse(AssertionException.class, message, () -> Check.isEqual(1, 1l));
	}

	@Test
	public void checkIsEqualLongWithLong() {
		Check.isEqual(1l, 1l);
	}

	@Test
	public void checkIsEqualArrayPass() {
		Check.isEqual(new int[] { 1, 2 }, new int[] { 1, 2 });
	}

	@Test
	public void checkIsEqualArrayFail() {
		int[] actual = new int[] { 1 };
		int[] expected = new int[] { 1, 2, 3 };
		BombSquad.diffuse(AssertionException.class, "!=", () -> Check.isEqual(actual, expected));
	}
}
