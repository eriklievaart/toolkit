package com.eriklievaart.toolkit.bean.api;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;

public interface BeanValidator<A extends Annotation> {

	public Optional<BeanValidationMessage> check(AnnotatedField<A> field, Object target);

	public List<Class<?>> getSupportedTypes();

	public Class<A> getAnnotation();

	public default boolean validateNull() {
		return false;
	}
}