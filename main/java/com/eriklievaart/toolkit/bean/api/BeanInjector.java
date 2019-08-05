package com.eriklievaart.toolkit.bean.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.bean.impl.EmailBeanValidator;
import com.eriklievaart.toolkit.bean.impl.NotBlankBeanValidator;
import com.eriklievaart.toolkit.bean.impl.NotEmptyBeanValidator;
import com.eriklievaart.toolkit.bean.impl.RegexBeanValidator;
import com.eriklievaart.toolkit.bean.impl.RequiredBeanValidator;
import com.eriklievaart.toolkit.bean.impl.SizeBeanValidator;
import com.eriklievaart.toolkit.convert.api.Constructor;
import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.reflect.api.FieldTool;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;

public class BeanInjector {

	private Map<String, String> values = new Hashtable<>();
	private Map<Class<? extends Annotation>, BeanValidator<?>> validators = new Hashtable<>();
	private Map<Class<?>, Constructor<?>> constructors = new Hashtable<>();

	public BeanInjector() {
		this(new Hashtable<>());
	}

	public BeanInjector(Map<String, String> values) {
		this.values.putAll(values);
		registerDefaultValidators();
	}

	private void registerDefaultValidators() {
		validator(new SizeBeanValidator());
		validator(new EmailBeanValidator());
		validator(new RegexBeanValidator());
		validator(new RequiredBeanValidator());
		validator(new NotEmptyBeanValidator());
		validator(new NotBlankBeanValidator());
	}

	public BeanInjector validator(BeanValidator<?> validator) {
		Check.notNull(validator);
		CheckCollection.notEmpty(validator.getSupportedTypes());
		validators.put(validator.getAnnotation(), validator);
		return this;
	}

	public BeanInjector constructor(Constructor<?> constructor) {
		Check.notNull(constructor);
		Check.notNull(constructor.getLiteral());
		constructors.put(constructor.getLiteral(), constructor);
		return this;
	}

	public BeanInjector inject(Object object) {
		Check.notNull(object);
		Check.notNull(object);

		Class<?> literal = object.getClass();
		for (String name : FieldTool.getFieldNames(literal)) {
			if (values.containsKey(name)) {
				String value = values.get(name);
				Field field = FieldTool.getField(literal, name);
				InstanceTool.injectField(object, field, customConvert(value, field.getType()));
			}
		}
		return this;
	}

	private Object customConvert(String value, Class<?> type) {
		try {
			return constructors.containsKey(type) ? constructors.get(type).constructObject(value) : value;
		} catch (ConversionException e) {
			throw new ReflectException("Conversion failed for type " + type.getSimpleName(), e);
		}
	}

	public void validate(Object object) {
		Check.notNull(object);

		List<BeanValidationMessage> errors = NewCollection.list();
		validators.values().forEach(validator -> errors.addAll(validate(object, validator)));
		if (!errors.isEmpty()) {
			throw new BeanValidationException(errors);
		}
	}

	private <A extends Annotation, E> List<BeanValidationMessage> validate(Object o, BeanValidator<A> validator) {
		List<BeanValidationMessage> errors = NewCollection.list();
		for (AnnotatedField<A> field : AnnotationTool.getFieldsAnnotatedWith(o.getClass(), validator.getAnnotation())) {
			if (field.getValue(o) != null || validator.validateNull()) {
				checkAnnotationType(validator, field);
				validator.check(field, o).ifPresent(error -> errors.add(error));
			}
		}
		return errors;
	}

	private <E, A extends Annotation> void checkAnnotationType(BeanValidator<A> validator, AnnotatedField<A> field) {
		Class<?> actual = field.getMember().getType();
		for (Class<?> supported : validator.getSupportedTypes()) {
			if (LiteralTool.isAssignable(actual, supported)) {
				return;
			}
		}
		String annotation = validator.getAnnotation().getSimpleName();
		String message = "$: @$ cannot be assigned to fields of type $";
		throw new AssertionException(message, field.getName(), annotation, actual.getSimpleName());
	}
}
