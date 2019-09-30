package com.eriklievaart.toolkit.lang.api.str;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class StringBuilderWrapperU {

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
}
