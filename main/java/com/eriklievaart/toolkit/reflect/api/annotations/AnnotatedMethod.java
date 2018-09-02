package com.eriklievaart.toolkit.reflect.api.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;

/**
 * Wrapper for a Method that has an Annotation.
 * 
 * @param <A>
 *            type of the annotation.
 * 
 * @author Erik Lievaart
 */
@Immutable
public class AnnotatedMethod<A extends Annotation> extends AnnotatedMember<Method, A> {

	AnnotatedMethod(final Method method, final A annotation) {
		super(method, annotation);
	}

	/**
	 * Get a MethodWrapper for the annotated Method on the specified Object.
	 */
	public MethodWrapper getMethodWrapper(final Object instance) {
		return InstanceTool.getMethodWrapper(getName(), instance);
	}

	@Override
	public <O extends Annotation> O getAnnotation(Class<O> other) {
		return getMember().getAnnotation(other);
	}

}
