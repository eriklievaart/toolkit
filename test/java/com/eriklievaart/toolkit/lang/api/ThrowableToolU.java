package com.eriklievaart.toolkit.lang.api;

import java.io.IOException;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.ThrowableTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class ThrowableToolU {

	@Test
	public void getRootCause() {
		Exception parent = new Exception(new RuntimeException(new IOException()));
		Check.isEqual(ThrowableTool.getRootCause(parent).getClass(), IOException.class);
	}

	@Test
	public void throwableToString() {
		String string = ThrowableTool.toString(new Exception());
		CheckStr.contains(string, getClass().getCanonicalName());
		CheckStr.contains(string, "org.junit");
	}

	@Test
	public void throwableToStringNested() {
		Error exception = new Error("err", new RuntimeException("rex", new IOException("iox")));
		String string = ThrowableTool.toString(exception);
		CheckStr.contains(string, getClass().getCanonicalName());
		CheckStr.contains(string, "org.junit");
		CheckStr.contains(string, "java.lang.Error => err");
		CheckStr.contains(string, "caused by java.lang.RuntimeException => rex");
		CheckStr.contains(string, "caused by java.io.IOException => iox");
	}

	@Test
	public void throwableAppend() {
		StringBuilder builder = new StringBuilder();
		ThrowableTool.append(new Exception(), builder);
		CheckStr.contains(builder.toString(), getClass().getCanonicalName());
		CheckStr.contains(builder.toString(), "org.junit");
	}
}