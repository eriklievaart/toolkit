package com.eriklievaart.toolkit.lang.api;

import java.lang.reflect.Array;
import java.util.List;
import java.util.function.Supplier;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
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

	public static String object(Object object) {
		if (object == null) {
			return Str.NULL;
		}
		if (object instanceof String) {
			return (String) object;
		}
		if (object instanceof Supplier) {
			Supplier<?> supplier = (Supplier<?>) object;
			return object(supplier.get());
		}
		if (object.getClass().isArray()) {
			int length = Array.getLength(object);
			List<String> elements = NewCollection.list();
			for (int i = 0; i < length; i++) {
				elements.add(object(Array.get(object, i)));
			}
			return elements.toString();
		}
		return object.toString();
	}
}