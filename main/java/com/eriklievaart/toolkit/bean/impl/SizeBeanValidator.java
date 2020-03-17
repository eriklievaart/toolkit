package com.eriklievaart.toolkit.bean.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.bean.api.BeanValidationMessage;
import com.eriklievaart.toolkit.bean.api.BeanValidator;
import com.eriklievaart.toolkit.bean.api.annotate.Size;
import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public class SizeBeanValidator implements BeanValidator<Size> {

	@Override
	public Optional<BeanValidationMessage> check(AnnotatedField<Size> field, Object target) {
		Object assigned = field.getValue(target);
		if (assigned == null) {
			return Optional.empty();
		}
		long value = getValue(assigned);
		Field member = field.getMember();

		long min = field.getAnnotation().min();
		if (value < min) {
			String message = Str.sub("$($) is required to be at least $", member.getName(), value, min);
			return Optional.of(new BeanValidationMessage(member, message));
		}

		long max = field.getAnnotation().max();
		if (value > max) {
			String message = Str.sub("$($) is required to be at most $", member.getName(), value, min);
			return Optional.of(new BeanValidationMessage(member, message));
		}
		return Optional.empty();
	}

	private long getValue(Object instance) {
		if (instance instanceof Number) {
			Number number = (Number) instance;
			return number.longValue();
		}
		if (instance instanceof String) {
			String string = (String) instance;
			return string.length();
		}
		throw new FormattedException("@Size cannot be applied to fields of type " + instance.getClass());
	}

	@Override
	public List<Class<?>> getSupportedTypes() {
		return Arrays.asList(Number.class, String.class, int.class, long.class, byte.class);
	}

	@Override
	public Class<Size> getAnnotation() {
		return Size.class;
	}
}