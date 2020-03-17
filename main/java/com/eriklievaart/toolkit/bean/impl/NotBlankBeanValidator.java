package com.eriklievaart.toolkit.bean.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.bean.api.BeanValidationMessage;
import com.eriklievaart.toolkit.bean.api.BeanValidator;
import com.eriklievaart.toolkit.bean.api.annotate.NotBlank;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public class NotBlankBeanValidator implements BeanValidator<NotBlank> {

	@Override
	public Optional<BeanValidationMessage> check(AnnotatedField<NotBlank> field, Object target) {
		if (Str.isBlank((String) field.getValue(target))) {
			String message = Str.sub("$ is not allowed to be blank", field.getName());
			return Optional.of(new BeanValidationMessage(field.getMember(), message));
		}
		return Optional.empty();
	}

	@Override
	public List<Class<?>> getSupportedTypes() {
		return Arrays.asList(String.class);
	}

	@Override
	public Class<NotBlank> getAnnotation() {
		return NotBlank.class;
	}

	@Override
	public boolean validateNull() {
		return true;
	}
}