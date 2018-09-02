package com.eriklievaart.toolkit.reflect.api.string;

import java.awt.Point;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.reflect.api.string.ReflectionRepresentation;
import com.eriklievaart.toolkit.reflect.api.string.ReflectionRepresentationTool;

public class ReflectionRepresentationU {

	@Test
	public void test() throws Exception {
		String string = "java.awt.Point#x";

		ReflectionRepresentation representation = ReflectionRepresentationTool.fromString(string);

		CheckStr.isEqual(representation.getLiteralName(), "java.awt.Point");
		Check.isEqual(representation.getLiteral(), Point.class);
		CheckStr.isEqual(representation.getMemberName(), "x");
		CheckStr.isEqual(representation.getField().getName(), "x");
	}
}
