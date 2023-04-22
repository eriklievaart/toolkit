package com.eriklievaart.toolkit.lang.api.str;

import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class StringBuilderWrapperU {

	@Test
	public void substringAt() {
		StringBuilderWrapper testable = new StringBuilderWrapper("0123456789");

		Check.isTrue(testable.substringAt(0, "0"));
		Check.isTrue(testable.substringAt(0, "01234"));
		Check.isTrue(testable.substringAt(0, "0123456789"));
		Check.isFalse(testable.substringAt(0, "01235"));

		Check.isFalse(testable.substringAt(5, "012345"));
		Check.isTrue(testable.substringAt(5, "56789"));
	}

	@Test
	public void substringAtOutOfBounds() {
		StringBuilderWrapper testable = new StringBuilderWrapper("0");
		Check.isTrue(testable.substringAt(0, "0"));
		Check.isFalse(testable.substringAt(1, "0")); // out of bounds
	}

	@Test
	public void charAt() {
		StringBuilderWrapper testable = new StringBuilderWrapper("hello");

		Check.isEqual(testable.charAt(0), 'h');
		Check.isEqual(testable.charAt(1), 'e');
		Check.isEqual(testable.charAt(2), 'l');
		Check.isEqual(testable.charAt(3), 'l');
		Check.isEqual(testable.charAt(4), 'o');

		Check.isEqual(testable.charAt(-5), 'h');
		Check.isEqual(testable.charAt(-4), 'e');
		Check.isEqual(testable.charAt(-3), 'l');
		Check.isEqual(testable.charAt(-2), 'l');
		Check.isEqual(testable.charAt(-1), 'o');
	}

	@Test
	public void insert() {
		Check.isEqual(new StringBuilderWrapper("123456").insert(0, "abc").toString(), "abc123456");
		Check.isEqual(new StringBuilderWrapper("123456").insert(1, "abc").toString(), "1abc23456");
		Check.isEqual(new StringBuilderWrapper("123456").insert(2, "abc").toString(), "12abc3456");
		Check.isEqual(new StringBuilderWrapper("123456").insert(-1, "abc").toString(), "12345abc6");
		Check.isEqual(new StringBuilderWrapper("123456").insert(-2, "abc").toString(), "1234abc56");
	}

	@Test
	public void deleteAt() {
		StringBuilderWrapper testable = new StringBuilderWrapper("0123456789");

		String deleted = testable.deleteAt(8, 2);
		Check.isEqual(testable.toString(), "01234567");
		Check.isEqual(deleted, "89");

		deleted = testable.deleteAt(0, 3);
		Check.isEqual(testable.toString(), "34567");
		Check.isEqual(deleted, "012");
	}

	@Test
	public void endsWith() {
		StringBuilderWrapper testable = new StringBuilderWrapper("hello");
		Check.isTrue(testable.endsWith("lo"));
		Check.isFalse(testable.endsWith("ld"));

		testable.append(" world");
		Check.isFalse(testable.endsWith("lo"));
		Check.isTrue(testable.endsWith("ld"));
	}

	@Test
	public void endsWithBorderCases() {
		StringBuilderWrapper testable = new StringBuilderWrapper("hello");
		Check.isTrue(testable.endsWith("hello"));
		Check.isFalse(testable.endsWith("hello world"));
	}

	@Test
	public void replaceChar() {
		StringBuilderWrapper testable = new StringBuilderWrapper("hello");
		testable.replaceChar(1, 'a');
		Check.isEqual(testable.toString(), "hallo");

		testable.replaceChar(-5, 'w');
		testable.replaceChar(-1, 'y');
		Check.isEqual(testable.toString(), "wally");
	}

	@Test
	public void deleteLast() {
		StringBuilderWrapper testable = new StringBuilderWrapper("hello");
		testable.deleteLast(1);
		Check.isEqual(testable.toString(), "hell");

		testable.deleteLast(3);
		Check.isEqual(testable.toString(), "h");

		testable.deleteLast(1);
		Check.isEqual(testable.toString(), "");
	}

	@Test
	public void appendChar() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.append('a');
		CheckStr.isEqual(sbw.toString(), "a");
	}

	@Test
	public void appendObject() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.append(new Object());
		CheckStr.contains(sbw.toString(), "java.lang.Object");
	}

	@Test
	public void appendLineNoArgs() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.appendLine();
		CheckStr.isEqual(sbw.toString(), "\n");
	}

	@Test
	public void appendLineSingle() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.appendLine("a");
		CheckStr.isEqual(sbw.toString(), "a\n");
	}

	@Test
	public void appendLineMultiple() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.appendLine("a", "b", "c");
		CheckStr.isEqual(sbw.toString(), "abc\n");
	}

	@Test
	public void appendJoined() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		List<String> collection = ListTool.of("foo", "bar", "baz");
		sbw.appendJoined(collection, "@");
		CheckStr.isEqual(sbw.toString(), "foo@bar@baz");
	}

	@Test
	public void appendTag() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.append("some text");
		sbw.appendTag("br");
		CheckStr.isEqual(sbw.toString(), "some text<br/>");
	}

	@Test
	public void appendTagWithContents() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.appendTag("p", "some text");
		CheckStr.isEqual(sbw.toString(), "<p>some text</p>");
	}

	@Test
	public void appendTagOpen() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.appendTagOpen("p");
		sbw.append("some text");
		CheckStr.isEqual(sbw.toString(), "<p>some text");
	}

	@Test
	public void appendTagOpenWithAttributes() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.appendTagOpen("div", MapTool.of("id", "1", "class", "red"));
		CheckStr.isEqual(sbw.toString(), "<div id=\"1\" class=\"red\">");
	}

	@Test
	public void appendTagClose() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.append("<p>some text");
		sbw.appendTagClose("p");
		CheckStr.isEqual(sbw.toString(), "<p>some text</p>");
	}

	@Test
	public void sub() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.sub("$:$", "a", "b");
		CheckStr.isEqual(sbw.toString(), "a:b");
	}

	@Test
	public void subLine() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.subLine("$:$", "a", "b");
		CheckStr.isEqual(sbw.toString(), "a:b\n");
	}

	@Test
	public void clearEmpty() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.clear();
		CheckStr.isEmpty(sbw.toString());
	}

	@Test
	public void clearContent() {
		StringBuilderWrapper sbw = new StringBuilderWrapper();
		sbw.append('u');
		sbw.clear();
		CheckStr.isEmpty(sbw.toString());
	}

	@Test
	public void reset() {
		StringBuilderWrapper sbw = new StringBuilderWrapper("foo");
		sbw.reset("bar");
		CheckStr.isEqual(sbw.toString(), "bar");
	}

	@Test
	public void resetLine() {
		StringBuilderWrapper sbw = new StringBuilderWrapper("foo");
		sbw.resetLine("bar");
		CheckStr.isEqual(sbw.toString(), "bar\n");
	}

	@Test
	public void length() {
		StringBuilderWrapper sbw = new StringBuilderWrapper("12345");
		Check.isEqual(sbw.length(), 5);
	}

	@Test
	public void isEmptyTrue() {
		Check.isTrue(new StringBuilderWrapper().isEmpty());
	}

	@Test
	public void isEmptyFalse() {
		Check.isFalse(new StringBuilderWrapper("data").isEmpty());
	}

	@Test
	public void isBlank() {
		Check.isTrue(new StringBuilderWrapper().isBlank());
		Check.isTrue(new StringBuilderWrapper("").isBlank());
		Check.isTrue(new StringBuilderWrapper(" ").isBlank());
		Check.isTrue(new StringBuilderWrapper(" \r\n").isBlank());
		Check.isFalse(new StringBuilderWrapper("n").isBlank());
		Check.isFalse(new StringBuilderWrapper("data").isBlank());
	}
}
