package com.eriklievaart.toolkit.lang.api;

import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class ToString {

	public static String simple(Object obj, String format, Object... args) {
		Check.notNull(obj);

		String simple = obj.getClass().getSimpleName();

		List<String> arguments = ListTool.of(simple);
		for (Object arg : args) {
			arguments.add(Obj.toString(arg));
		}
		return Str.sub(format, arguments.toArray());
	}
}
