package com.eriklievaart.toolkit.lang.api.str;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class StringBuilderWrapperU {

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
}
