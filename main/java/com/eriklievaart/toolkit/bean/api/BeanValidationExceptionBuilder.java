package com.eriklievaart.toolkit.bean.api;

import java.util.Arrays;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class BeanValidationExceptionBuilder {

	private String field;

	public BeanValidationExceptionBuilder(String field) {
		this.field = field;
	}

	public void on(boolean fail, String format, Object... args) {
		if (fail) {
			throw new BeanValidationException(Arrays.asList(new BeanValidationMessage(field, Str.sub(format, args))));
		}
	}

	public void unless(boolean success, String format, Object... args) {
		on(!success, format, args);
	}
}
