package com.eriklievaart.toolkit.bean.api;

import java.lang.annotation.Annotation;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.bean.impl.EmailBeanValidator;
import com.eriklievaart.toolkit.bean.impl.NotBlankBeanValidator;
import com.eriklievaart.toolkit.bean.impl.NotEmptyBeanValidator;
import com.eriklievaart.toolkit.bean.impl.RegexBeanValidator;
import com.eriklievaart.toolkit.bean.impl.RequiredBeanValidator;
import com.eriklievaart.toolkit.bean.impl.SizeBeanValidator;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.reflect.api.FieldTool;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;

public class BeanInjector {

	private Map<String, String> values = new Hashtable<>();
	private Map<Class<? extends Annotation>, BeanValidator<?>> validators = new Hashtable<>();

	public BeanInjector() {
		this(new Hashtable<>());
	}

	public BeanInjector(Map<String, String> values) {
		this.values.putAll(values);
		registerDefaultValidators();
	}

	private void registerDefaultValidators() {
		addValidator(new SizeBeanValidator());
		addValidator(new EmailBeanValidator());
		addValidator(new RegexBeanValidator());
		addValidator(new RequiredBeanValidator());
		addValidator(new NotEmptyBeanValidator());
		addValidator(new NotBlankBeanValidator());
	}

	public void addValidator(BeanValidator<?> validator) {
		Check.notNull(validator);
		CheckCollection.notEmpty(validator.getSupportedTypes());
		validators.put(validator.getAnnotation(), validator);
	}

	public BeanInjector inject(Object object) {
		Check.notNull(object);
		Check.notNull(object);

		Class<?> literal = object.getClass();
		for (String name : FieldTool.getFieldNames(literal)) {
			if (values.containsKey(name)) {
				InstanceTool.injectField(object, FieldTool.getField(literal, name), values.get(name));
			}
		}
		return this;
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
