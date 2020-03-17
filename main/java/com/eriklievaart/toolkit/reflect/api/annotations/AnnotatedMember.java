package com.eriklievaart.toolkit.reflect.api.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;

@Immutable
abstract class AnnotatedMember<M extends Member, A extends Annotation> {

	private final A annotation;
	private final M member;

	AnnotatedMember(final M member, final A annotation) {
		Check.notNull(member);
		Check.notNull(annotation);

		this.member = member;
		this.annotation = annotation;
	}

	/**
	 * Get the Annotation on the member.
	 */
	public A getAnnotation() {
		return annotation;
	}

	/**
	 * Get an annotation of another type.
	 */
	public abstract <O extends Annotation> O getAnnotation(Class<O> other);

	/**
	 * Get the Member that is annotated.
	 */
	public M getMember() {
		return member;
	}

	/**
	 * Get the name of the member.
	 */
	public String getName() {
		return member.getName();
	}

	/**
	 * Get the Class containing the member.
	 */
	public Class<?> getDeclaringClass() {
		return getMember().getDeclaringClass();
	}

	@Override
	public String toString() {
		return String.format("[%s]{ %s }", this.getClass().getSimpleName(), getName());
	}
}