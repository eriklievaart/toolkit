package com.eriklievaart.toolkit.lang.api.str;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.str.StringEscape;

public class StringEscapeU {

	public static void main(final String[] args) {
		new StringEscape(MapTool.of('e', '=')).unescape("a\\");
	}

	@Test
	public void escapeNothing() {
		String escaped = new StringEscape(MapTool.of('b', '\\')).escape("apple");
		Check.isEqual(escaped, "apple");
	}

	@Test
	public void escapeSingle() {
		String escaped = new StringEscape(MapTool.of('b', '\\')).escape("apple\\yummy");
		Check.isEqual(escaped, "apple\\byummy");
	}

	@Test
	public void unescapeNothing() {
		String escaped = new StringEscape(MapTool.of('e', '=')).unescape("apple");
		Check.isEqual(escaped, "apple");
	}

	@Test
	public void unescapeSingle() {
		String escaped = new StringEscape(MapTool.of('e', '=')).unescape("apple\\eyummy");
		Check.isEqual(escaped, "apple=yummy");
	}

	@Test
	public void unescapeDouble() {
		Check.isEqual(new StringEscape(MapTool.of('b', '\\', 's', ' ')).unescape("\\b\\s"), "\\ ");
	}

	@Test
	public void unescapeEscapeCharacter() {
		Check.isEqual(new StringEscape(MapTool.of('\\', '\\')).unescape("\\\\\\\\"), "\\\\");
	}

	@Test(expected = AssertionException.class)
	public void unescapeInvalidEscapeSequence() {
		new StringEscape(MapTool.of('e', '=')).unescape("apple\\'yummy");
	}

	@Test(expected = AssertionException.class)
	public void unescapeIncompleteEscapeSequence() {
		new StringEscape(MapTool.of('e', '=')).unescape("apple\\");
	}

	@Test
	public void invalidEscapeSequence() {
		Check.isTrue(new StringEscape(MapTool.of('e', '=')).hasInvalidEscapeSequence("\\invalid escape"));
	}

	@Test
	public void invalidTrailingEscapeSequence() {
		Check.isTrue(new StringEscape(MapTool.of('e', '=')).hasInvalidEscapeSequence("missing control char\\"));
	}

	@Test
	public void validEscapeSequence() {
		Check.isFalse(new StringEscape(MapTool.of('e', '=')).hasInvalidEscapeSequence("valid \\escape"));
	}

	@Test
	public void validEscapeSequenceEmpty() {
		Check.isFalse(new StringEscape(MapTool.of('e', '=')).hasInvalidEscapeSequence(""));
	}

	@Test
	public void javadocExample() {

		StringEscape string = new StringEscape(MapTool.of('s', ' '), '\\');
		String escaped = string.escape("has whitespace"); // result: "has\swhitespace"
		String unescaped = string.unescape("has\\swhitespace"); // result: "has whitespace"

		Check.isEqual(escaped, "has\\swhitespace");
		Check.isEqual(unescaped, "has whitespace");
	}
}
