package com.eriklievaart.toolkit.reflect.api;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.reflect.api.FieldTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;

public class FieldToolU {

	public static final Integer CONSTANT = 15;

	@Test(expected = ReflectException.class)
	public void readFieldNegative() {
		FieldTool.getConstant(getClass(), "MISSING");
	}

	@Test
	public void readConstant() {
		Object constant = FieldTool.getConstant(getClass(), "CONSTANT");
		Check.isEqual(constant, CONSTANT);
	}

	@Test
	public void getConstants() {
		Map<String, ?> constants = FieldTool.getConstants(Constants.class);
		Check.isTrue(constants.containsKey("string"), "Constant 'string' not found");
		Check.isTrue(constants.containsKey("integer"), "Constant 'integer' not found");
		Check.isTrue(constants.size() == 2, "Constants size expected size 2, but was " + constants);
	}
}
