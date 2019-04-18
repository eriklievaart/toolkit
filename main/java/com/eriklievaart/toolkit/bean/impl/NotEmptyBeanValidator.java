package com.eriklievaart.toolkit.bean.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.bean.api.BeanValidationMessage;
import com.eriklievaart.toolkit.bean.api.BeanValidator;
import com.eriklievaart.toolkit.bean.api.annotate.NotEmpty;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public class NotEmptyBeanValidator implements BeanValidator<NotEmpty> {

	@Override
	public Optional<BeanValidationMessage> check(AnnotatedField<NotEmpty> field, Object target) {
		if (Str.isEmpty((String) field.getValue(target))) {
			String message = Str.sub("$ is not allowed to be empty", field.getName());
			return Optional.of(new BeanValidationMessage(field.getMember(), message));
		}
		return Optional.empty();
	}

	@Override
	public List<Class<?>> getSupportedTypes() {
		return Arrays.asList(String.class);
	}

	@Override
	public Class<NotEmpty> getAnnotation() {
		return NotEmpty.class;
	}

	@Override
	public boolean validateNull() {
		return true;
	}
}
