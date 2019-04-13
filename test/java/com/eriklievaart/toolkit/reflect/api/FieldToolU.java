package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

@SuppressWarnings("unused")
public class FieldToolU {

	public static final Integer CONSTANT = 15;

	@Test
	public void getFieldNames() {
		class Inner {
			private String alpha;
			private String beta;
		}
		Assertions.assertThat(FieldTool.getFieldNames(Inner.class)).contains("alpha", "beta");
	}

	@Test
	public void getFieldByType() {
		class Inner {
			private int alpha;
			private String beta;
		}
		Check.isEqual(FieldTool.getFieldByType(Inner.class, String.class).getName(), "beta");
		Check.isEqual(FieldTool.getFieldByType(Inner.class, int.class).getName(), "alpha");
	}

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

	@Test
	public void getGenericLiteral() {
		class Dummy {
			private List<Integer> extract;
		}
		Field field = FieldTool.getField(Dummy.class, "extract");
		Class<?> literal = FieldTool.getGenericLiteral(field);
		Check.isEqual(literal.getSimpleName(), "Integer");
	}

	@Test
	public void getGenericLiterals() {
		class Dummy {
			private Map<String, Integer> extract;
		}
		Field field = FieldTool.getField(Dummy.class, "extract");
		List<Class<?>> literals = FieldTool.getGenericLiterals(field);
		Assertions.assertThat(literals).containsExactly(String.class, Integer.class);
	}
}
