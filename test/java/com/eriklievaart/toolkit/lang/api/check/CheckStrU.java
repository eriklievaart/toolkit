package com.eriklievaart.toolkit.lang.api.check;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.mock.BombSquad;

public class CheckStrU {

	@Test
	public void isEqual() {
		CheckStr.isEqual("abc", "abc");
		BombSquad.diffuse(AssertionException.class, () -> CheckStr.isEqual("abc", "ABC"));
		BombSquad.diffuse(AssertionException.class, () -> CheckStr.isEqual("abc", "a b c"));
	}

	@Test
	public void isEqualIgnoreCase() {
		CheckStr.isEqualIgnoreCase("abc", "abc");
		CheckStr.isEqualIgnoreCase("abc", "ABC");
		BombSquad.diffuse(AssertionException.class, () -> CheckStr.isEqualIgnoreCase("abc", "a b c"));
	}

	@Test
	public void isLengthPass() {
		CheckStr.isLength("abc", 3);
		BombSquad.diffuse("expected length 2", () -> CheckStr.isLength("abc", 2));
		BombSquad.diffuse("expected length 4", () -> CheckStr.isLength("abc", 4));
	}

	@Test
	public void notEqualPass() {
		CheckStr.notEqual("abc", "def");
	}

	@Test(expected = AssertionException.class)
	public void notEqualFail() {
		CheckStr.notEqual("abc", "abc");
	}

	@Test
	public void isIdentifierPass() {
		CheckStr.isIdentifier("abl_SDH");
	}

	@Test(expected = AssertionException.class)
	public void isIdentifierFail() {
		CheckStr.isIdentifier("in;valid");
	}

	@Test
	public void notBlankPass() {
		CheckStr.notBlank("abc");
	}

	@Test
	public void notBlankFail() {
		BombSquad.diffuse("argument is blank", () -> CheckStr.notBlank(" \t "));
	}

	@Test
	public void notBlankEmpty() {
		BombSquad.diffuse("argument is blank", () -> CheckStr.notBlank(""));
	}

	@Test
	public void notBlankNull() {
		BombSquad.diffuse("argument is blank", () -> CheckStr.notBlank(null));
	}

	@Test
	public void notBlankFailWithmessage() {
		BombSquad.diffuse("custom message", () -> CheckStr.notBlank(" \t ", "custom message"));
	}
}
