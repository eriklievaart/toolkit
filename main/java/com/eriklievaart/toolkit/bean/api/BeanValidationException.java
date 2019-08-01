package com.eriklievaart.toolkit.bean.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanValidationException extends RuntimeException {

	private final Map<String, String> errors;

	public BeanValidationException(List<BeanValidationMessage> errors) {
		super(createMessage(errors));
		this.errors = fieldToMessage(errors);
	}

	private static String createMessage(List<BeanValidationMessage> messages) {
		return "invalid: " + messages.stream().map(m -> m.getMessage()).collect(Collectors.toList());
	}

	private Map<String, String> fieldToMessage(List<BeanValidationMessage> list) {
		return list.stream().collect(Collectors.toMap(m -> m.getField(), m -> m.getMessage()));
	}

	public Map<String, String> getErrors() {
		return Collections.unmodifiableMap(errors);
	}

	public static BeanValidationExceptionBuilder forField(String field) {
		return new BeanValidationExceptionBuilder(field);
	}
}
