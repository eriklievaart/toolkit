package com.eriklievaart.toolkit.lang.api.check;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class CheckStrU {

	@Test
	public void isIdentifierPass() {
		CheckStr.isIdentifier("abl_SDH");
	}

	@Test(expected=AssertionException.class)
	public void isIdentifierFail() {
		CheckStr.isIdentifier("in;valid");
	}
}
