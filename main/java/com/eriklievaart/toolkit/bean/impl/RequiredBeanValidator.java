package com.eriklievaart.toolkit.bean.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.bean.api.BeanValidationMessage;
import com.eriklievaart.toolkit.bean.api.BeanValidator;
import com.eriklievaart.toolkit.bean.api.annotate.Required;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public class RequiredBeanValidator implements BeanValidator<Required> {

	@Override
	public Optional<BeanValidationMessage> check(AnnotatedField<Required> field, Object target) {
		if (field.getValue(target) == null) {
			String message = Str.sub("$ is a required field", field.getName());
			return Optional.of(new BeanValidationMessage(field.getMember(), message));
		}
		return Optional.empty();
	}

	@Override
	public List<Class<?>> getSupportedTypes() {
		return Arrays.asList(Object.class);
	}

	@Override
	public Class<Required> getAnnotation() {
		return Required.class;
	}

	@Override
	public boolean validateNull() {
		return true;
	}
}
