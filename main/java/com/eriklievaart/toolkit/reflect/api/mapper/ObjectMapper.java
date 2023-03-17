package com.eriklievaart.toolkit.reflect.api.mapper;

import java.lang.reflect.Field;
import java.util.Map;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converters;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.reflect.api.FieldTool;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;

public class ObjectMapper {

	public static Map<String, String> toMap(Object object) {
		return toMap(object, Converters.BASIC_CONVERTERS);
	}

	public static Map<String, String> toMap(Object object, Converters converters) {
		Check.notNull(object);

		try {
			Map<String, String> result = NewCollection.mapNotNull();
			Class<? extends Object> type = object.getClass();
			for (AnnotatedField<Property> field : AnnotationTool.getFieldsAnnotatedWith(type, Property.class)) {
				Object value = field.getValue(object);
				if (value != null) {
					result.put(field.getName(), converters.convertToString(value));
				}
			}
			ReflectException.on(result.isEmpty(), "Type $ not annotated with @Property", type);
			return result;

		} catch (ConversionException e) {
			throw new ReflectException(e.getMessage(), e);
		}
	}

	public static void inject(Object object, Map<String, String> values) {
		inject(object, values, Converters.BASIC_CONVERTERS);
	}

	public static void inject(Object object, Map<String, String> values, Converters converters) {
		Check.noneNull(object, values);
		Class<?> type = object.getClass();

		try {
			for (String name : values.keySet()) {
				Field field = FieldTool.getField(type, name);
				Property annotation = field.getAnnotation(Property.class);
				ReflectException.on(annotation == null, "Missing @Property on field $", field);
				InstanceTool.injectField(object, field, converters.to(field.getType(), values.get(name)));
			}
		} catch (ConversionException e) {
			throw new ReflectException(e.getMessage(), e);
		}
	}
}