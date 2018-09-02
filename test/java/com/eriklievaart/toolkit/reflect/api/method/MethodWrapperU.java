package com.eriklievaart.toolkit.reflect.api.method;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.reflect.api.GenericsTool;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;

public class MethodWrapperU {

	@Test
	public void getGenericTypes() {
		class Local {
			@SuppressWarnings("unused")
			public void method(final List<String> list) {
			}
		}
		MethodWrapper wrapper = InstanceTool.getMethodWrapper("method", new Local());
		Type[] types = wrapper.getGenericArgumentTypes();
		Check.isTrue(GenericsTool.isBaseType(types[0], List.class));
		Check.isTrue(GenericsTool.hasGenericTypes(types[0], String.class));
	}
}
