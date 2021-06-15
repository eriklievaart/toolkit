package com.eriklievaart.toolkit.lang.api.str;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class CharTokenizerU {

	@Test
	public void isBlank() {
		Check.isTrue(new CharTokenizer("").isBlank());
		Check.isTrue(new CharTokenizer(" ").isBlank());
		Check.isTrue(new CharTokenizer("\t").isBlank());
		Check.isFalse(new CharTokenizer(" a ").isBlank());
	}

	@Test
	public void nextNonWhitespaceToken() {
		CharTokenizer testable = new CharTokenizer("a b");
		Check.isEqual(testable.nextNonWhitespaceToken().get(), "a");

		testable.skipWhitespace();
		Check.isEqual(testable.nextNonWhitespaceToken().get(), "b");
	}

	@Test
	public void skipAndCount() {
		Check.isEqual(new CharTokenizer("").skipAndCount('\t'), 0);
		Check.isEqual(new CharTokenizer(" ").skipAndCount('\t'), 0);
		Check.isEqual(new CharTokenizer("\t").skipAndCount('\t'), 1);
		Check.isEqual(new CharTokenizer("abc").skipAndCount('\t'), 0);
		Check.isEqual(new CharTokenizer(" abc").skipAndCount('\t'), 0);
		Check.isEqual(new CharTokenizer("\tabc").skipAndCount('\t'), 1);
		Check.isEqual(new CharTokenizer("\t\t\tabc").skipAndCount('\t'), 3);
	}

	@Test
	public void nextNonNewLineToken() {
		CharTokenizer testable = new CharTokenizer("a b\nc d");
		Check.isEqual(testable.nextNonNewLineToken().get(), "a b");
	}

	@Test
	public void nextCharacter() {
		CharTokenizer testable = new CharTokenizer("a b\nc d");
		Check.isEqual(testable.nextCharacter(), 'a');
		Check.isEqual(testable.nextCharacter(), ' ');
		Check.isEqual(testable.nextCharacter(), 'b');
		Check.isEqual(testable.nextCharacter(), '\n');
	}

	@Test
	public void nextTokenUntilAny() {
		CharTokenizer testable = new CharTokenizer("a b\nc d");
		Check.isEqual(testable.nextTokenUntilAny('\n').get(), "a b");
		Check.isEqual(testable.nextTokenUntilAny(' ').get(), "\nc");
	}

	@Test
	public void skipWhitespace() {
		CharTokenizer testable = new CharTokenizer(" \ta b");
		testable.skipWhitespace();
		Check.isEqual(testable.toString(), "a b");
	}
}
