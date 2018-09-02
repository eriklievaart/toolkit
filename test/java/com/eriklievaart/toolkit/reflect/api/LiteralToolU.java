package com.eriklievaart.toolkit.reflect.api;

import java.awt.Point;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;

public class LiteralToolU {

	@Test
	public void getLiteral() {
		String clazz = "java.lang.String";
		Class<?> literal = LiteralTool.getLiteral(clazz);
		Check.isEqual(String.class, literal);
	}

	@Test
	public void newInstance() {
		String clazz = "java.awt.Point";
		Point point = (Point) LiteralTool.newInstance(clazz);
		Check.notNull(point);
	}

	@Test
	public void newPopulatedInstanceProperties() {
		Thread thread = LiteralTool.newPopulatedInstance("java.lang.Thread", MapTool.of("priority", 5));
		Check.isEqual(thread.getPriority(), 5);
		Thread thread2 = LiteralTool.newPopulatedInstance("java.lang.Thread", MapTool.of("priority", 6));
		Check.isEqual(thread2.getPriority(), 6);
	}

	@Test
	public void newPopulatedInstanceFields() {
		Point point = LiteralTool.newPopulatedInstance("java.awt.Point", MapTool.of("x", 5));
		Check.isEqual(point.x, 5);
	}

	@Test(expected = ReflectException.class)
	public void newPopulatedInstanceNegative() {
		LiteralTool.newPopulatedInstance("java.lang.Thread", MapTool.of("brocolli", 6));
	}

	@Test
	public void isAssignable() {
		Check.isTrue(LiteralTool.isAssignable(Object.class, Object.class), "Object not assignable to Object");
		Check.isTrue(LiteralTool.isAssignable(Boolean.class, Object.class), "Boolean not assignable to Object");
		Check.notTrue(LiteralTool.isAssignable(Object.class, Boolean.class), "Object assignable to Boolean");
	}

	@Test
	public void getPackage() {
		Check.isEqual(LiteralTool.getPackage("java.lang.Boolean"), "java.lang");
		Check.isEqual(LiteralTool.getPackage("java.util.List"), "java.util");
		Check.isEqual(LiteralTool.getPackage(Thread.class), "java.lang");
	}

	@Test
	public void isAbstractYes() {
		Check.isTrue(LiteralTool.isAbstract(Runnable.class));
	}

	@Test
	public void isAbstractNo() {
		Check.isFalse(LiteralTool.isAbstract(Thread.class));
	}

}