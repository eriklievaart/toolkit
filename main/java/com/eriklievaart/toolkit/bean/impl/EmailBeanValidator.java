package com.eriklievaart.toolkit.bean.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.bean.api.BeanValidationMessage;
import com.eriklievaart.toolkit.bean.api.BeanValidator;
import com.eriklievaart.toolkit.bean.api.annotate.Email;
import com.eriklievaart.toolkit.convert.api.validate.EmailValidator;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public class EmailBeanValidator implements BeanValidator<Email> {

	@Override
	public Optional<BeanValidationMessage> check(AnnotatedField<Email> field, Object target) {
		String value = field.getValue(target).toString();
		if (new EmailValidator().isValid(value)) {
			return Optional.empty();
		}
		String message = Str.sub("$ does not contain a valid email adres.", field.getName());
		return Optional.of(new BeanValidationMessage(field.getMember(), message));
	}

	@Override
	public List<Class<?>> getSupportedTypes() {
		return Arrays.asList(String.class);
	}

	@Override
	public Class<Email> getAnnotation() {
		return Email.class;
	}
}
