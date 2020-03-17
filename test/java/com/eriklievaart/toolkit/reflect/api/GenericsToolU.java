package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

@SuppressWarnings("unused")
public class GenericsToolU {

	@Test
	public void getRawTypeObject() {
		class Inner {
			public void method(final Object object) {
			}
		}
		Type type = MethodTool.getMethod(Inner.class, "method").getGenericParameterTypes()[0];
		Check.isEqual(Object.class, GenericsTool.getLiteral(type));
	}

	@Test
	public void getRawTypeList() {
		class Inner {
			public void method(final List<?> list) {
			}
		}
		Type type = MethodTool.getMethod(Inner.class, "method").getGenericParameterTypes()[0];
		Check.isEqual(List.class, GenericsTool.getLiteral(type));
	}

	@Test
	public void getRawTypeMap() {
		class Inner {
			public void method(final Map<?, ?> map) {
			}
		}
		Type type = MethodTool.getMethod(Inner.class, "method").getGenericParameterTypes()[0];
		Check.isEqual(Map.class, GenericsTool.getLiteral(type));
	}

	@Test(expected = ReflectException.class)
	public void getGenericTypesFail() {
		class Local {
			private Thread thread;
		}
		Field field = LiteralTool.wrap(Local.class).getField("thread");
		GenericsTool.getGenericTypes(field.getGenericType());
	}

	@Test
	public void getGenericTypesSuccess() {
		class Local {
			private List<String> list;
		}
		Field field = LiteralTool.wrap(Local.class).getField("list");
		Check.isEqual(GenericsTool.getGenericTypes(field.getGenericType()), new Type[] { String.class });
	}

	@Test
	public void getGenericTypesDouble() {
		Type[] expected = new Type[] { String.class, Integer.class };
		class Local {
			private Map<String, Integer> list;
		}
		Field field = LiteralTool.wrap(Local.class).getField("list");
		Check.isEqual(GenericsTool.getGenericTypes(field.getGenericType()), expected);
	}

	@Test
	public void hasGenericTypesSuccess() {
		class Local {
			private List<String> list;
		}
		Field field = LiteralTool.wrap(Local.class).getField("list");
		Check.isTrue(GenericsTool.hasGenericTypes(field.getGenericType(), new Class<?>[] { String.class }));
	}

	@Test
	public void hasGenericTypesDouble() {
		Class<?>[] expected = new Class<?>[] { String.class, Integer.class };
		class Local {
			private Map<String, Integer> list;
		}
		Field field = LiteralTool.wrap(Local.class).getField("list");
		Check.isTrue(GenericsTool.hasGenericTypes(field.getGenericType(), expected));
	}

	@Test
	public void getGenericType() {
		class Local {
			private List<String> list;
		}
		Type type = GenericsTool.getGenericType(LiteralTool.wrap(Local.class).getField("list").getGenericType());
		Check.isEqual(type, String.class);
	}

	@Test(expected = ReflectException.class)
	public void getGenericTypeFailTooManyTypes() {
		class Local {
			private Map<String, String> map;
		}
		Type type = GenericsTool.getGenericType(LiteralTool.wrap(Local.class).getField("map").getGenericType());
	}

	@Test(expected = ReflectException.class)
	public void getGenericTypeFailTooFewTypes() {
		class Local {
			private Thread thread;
		}
		Type type = GenericsTool.getGenericType(LiteralTool.wrap(Local.class).getField("thread").getGenericType());
	}

	@Test
	public void isBaseTypeSuccess() {
		class Local {
			private List<Object> list;
		}
		Type type = LiteralTool.wrap(Local.class).getField("list").getType();
		Check.isTrue(GenericsTool.isBaseType(type, List.class));
	}

	@Test
	public void isBaseTypeFail() {
		class Local {
			private Thread list;
		}
		Type type = LiteralTool.wrap(Local.class).getField("list").getType();
		Check.isFalse(GenericsTool.isBaseType(type, List.class));
	}

	@Test
	public void isBaseTypeObject() {
		class Inner {
			public void method(final Object object) {
			}
		}
		Type type = MethodTool.getMethod(Inner.class, "method").getGenericParameterTypes()[0];
		Check.isTrue(GenericsTool.isBaseType(type, Object.class));
	}

	@Test
	public void isBaseTypeList() {
		class Inner {
			public void method(final List<?> list) {
			}
		}
		Type type = MethodTool.getMethod(Inner.class, "method").getGenericParameterTypes()[0];
		Check.isTrue(GenericsTool.isBaseType(type, List.class));
	}

	@Test
	public void isBaseTypeMap() {
		class Inner {
			public void method(final Map<?, ?> map) {
			}
		}
		Type type = MethodTool.getMethod(Inner.class, "method").getGenericParameterTypes()[0];
		Check.isTrue(GenericsTool.isBaseType(type, Map.class));
	}

	@Test
	public void isBaseTypeParameterized() {
		class Local {
			private List<String> list;
		}
		Type type = LiteralTool.wrap(Local.class).getField("list").getGenericType();
		Check.isTrue(GenericsTool.isBaseType(type, List.class));
	}
}