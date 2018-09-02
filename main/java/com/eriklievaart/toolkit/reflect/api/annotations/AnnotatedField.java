package com.eriklievaart.toolkit.reflect.api.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.InstanceWrapper;

/**
 * Wrapper for a Field that has an Annotation.
 * 
 * @param <A>
 *            type of the annotation.
 * 
 * @author Erik Lievaart
 */
@Immutable
public class AnnotatedField<A extends Annotation> extends AnnotatedMember<Field, A> {

	AnnotatedField(final Field field, final A annotation) {
		super(field, annotation);
	}

	/**
	 * Inject a value into the annotated Field.
	 */
	public void inject(final Object target, final Object value) {
		Check.isInstance(getDeclaringClass(), target);
		InstanceWrapper wrapper = InstanceTool.wrap(target);
		wrapper.injectField(getName(), value);
	}

	public Object getValue(Object target) {
		Check.isInstance(getDeclaringClass(), target);
		InstanceWrapper wrapper = InstanceTool.wrap(target);
		return wrapper.getFieldValue(getMember().getName());
	}

	/**
	 * Get the type of the field.
	 */
	public Class<?> getType() {
		return getMember().getType();
	}

	@Override
	public <O extends Annotation> O getAnnotation(Class<O> other) {
		return getMember().getAnnotation(other);
	}

}
