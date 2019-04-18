package com.eriklievaart.toolkit.lang.api.check;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.test.api.BombSquad;

public class CheckStrU {

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
