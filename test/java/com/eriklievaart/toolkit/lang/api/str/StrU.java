package com.eriklievaart.toolkit.lang.api.str;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.mock.Bomb;
import com.eriklievaart.toolkit.mock.BombSquad;

public class StrU {

	@Test
	public void isEmptyPass() {
		Check.isTrue(Str.isEmpty(""));
	}

	@Test
	public void isEmptyFail() {
		Check.isFalse(Str.isEmpty(" "));
	}

	@Test
	public void isBlankPass() {
		Check.isTrue(Str.isBlank(" \t\r\n"));
	}

	@Test
	public void isBlankFail() {
		Check.isFalse(Str.isBlank(" \t\r\na"));
	}

	@Test
	public void repeat0() {
		CheckStr.isBlank(Str.repeat("ignore", 0));
	}

	@Test
	public void repeat5() {
		Check.isEqual(Str.repeat("@", 5), "@@@@@");
	}

	@Test
	public void subWrongArgumentCount() {
		BombSquad.diffuse(AssertionException.class, Arrays.asList("expecting 3", "four"), new Bomb() {
			@Override
			public void explode() throws Exception {
				Str.sub("expecting 3 $ $ $", "one", "two", "three", "four");
			}
		});
	}

	@Test
	public void sub() {
		Check.isEqual(Str.sub("%-%-%", "01", "01", "1970 UTC"), "`01`-`01`-`1970 UTC`");
		Check.isEqual(Str.sub("replace $ with magic", "unquoted"), "replace unquoted with magic");
		Check.isEqual(Str.sub("replace % with magic", "quoted"), "replace `quoted` with magic");
		Check.isEqual(Str.sub("Black holes really suck...", new Object[] {}), "Black holes really suck...");
		Check.isEqual(Str.sub("nothing to do"), "nothing to do");
	}

	@Test
	public void trailing() {
		Check.isEqual(Str.trailing(null, '0'), "");
		Check.isEqual(Str.trailing("Missing separator", '-'), "Missing separator");
		Check.isEqual(Str.trailing("What do you call a fish with no eyes? A fsh", '?'), " A fsh");
	}

	@Test
	public void quote() {
		Check.isEqual(Str.quote(null), "<null>");
		Check.isEqual(Str.quote("test"), "`test`");
	}

	@Test
	public void sort() {
		Assertions.assertThat(Str.sort("c", "a", "B", null)).containsExactly(null, "B", "a", "c");
	}

	@Test
	public void sortIgnoreCase() {
		Assertions.assertThat(Str.sortIgnoreCase("c", "a", "B", null)).containsExactly(null, "a", "B", "c");
	}

	@Test
	public void splitOnCharEmpty() {
		Check.isEqual(Str.splitOnChar("", ' '), Arrays.asList(""));
	}

	@Test
	public void splitOnCharSingle() {
		Check.isEqual(Str.splitOnChar(" ", ' '), Arrays.asList("", ""));
	}

	@Test
	public void splitOnCharNormal() {
		Check.isEqual(Str.splitOnChar("a:b", ':'), Arrays.asList("a", "b"));
	}

	@Test
	public void appendLineNumbers() {
		Check.isEqual(Str.addLineNumbers("test"), "line 1: test\n");
	}

	@Test
	public void appendLineMulti() {
		Check.isEqual(Str.addLineNumbers("test\nmore"), "line 1: test\nline 2: more\n");
	}
}
