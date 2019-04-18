package com.eriklievaart.toolkit.bean.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.bean.api.BeanValidationMessage;
import com.eriklievaart.toolkit.bean.api.BeanValidator;
import com.eriklievaart.toolkit.bean.api.annotate.Regex;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public class RegexBeanValidator implements BeanValidator<Regex> {

	@Override
	public Optional<BeanValidationMessage> check(AnnotatedField<Regex> field, Object target) {
		String value = field.getValue(target).toString();
		String regex = field.getAnnotation().value();

		Check.notNull(regex, "No regex assigned to annotation on $", field.getMember());
		if (!value.matches(regex)) {
			return Optional.of(new BeanValidationMessage(field.getMember(), getMessage(field)));
		}
		return Optional.empty();
	}

	private String getMessage(AnnotatedField<Regex> field) {
		Regex annotation = field.getAnnotation();
		if (Str.notBlank(annotation.message())) {
			return annotation.message();
		}
		return Str.sub("$ must match $", field.getName(), annotation.value());
	}

	@Override
	public List<Class<?>> getSupportedTypes() {
		return Arrays.asList(String.class);
	}

	@Override
	public Class<Regex> getAnnotation() {
		return Regex.class;
	}
}
