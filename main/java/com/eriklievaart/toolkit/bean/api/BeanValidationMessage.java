package com.eriklievaart.toolkit.bean.api;

import java.lang.reflect.Field;

public class BeanValidationMessage {

	private String field;
	private String message;

	public BeanValidationMessage(Field field, String message) {
		this.field = field.getName();
		this.message = message;
	}

	BeanValidationMessage(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
}