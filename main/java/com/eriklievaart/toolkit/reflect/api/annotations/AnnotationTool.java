package com.eriklievaart.toolkit.reflect.api.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;
import com.eriklievaart.toolkit.reflect.api.FieldTool;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.MethodTool;

/**
 * Utility class for working with annotations on Class literals.
 *
 * @author Erik Lievaart
 */
public class AnnotationTool {
	private AnnotationTool() {
	}

	/**
	 * Find the Annotation on the class.
	 *
	 * @return the annotation found, or null if none.
	 */
	public static <A extends Annotation> A getLiteralAnnotation(final String clazz, final Class<A> annotation) {
		return getLiteralAnnotation(LiteralTool.getLiteral(clazz), annotation);
	}

	/**
	 * Find the Annotation on the class.
	 *
	 * @return the annotation found, or null if none.
	 */
	public static <A extends Annotation> A getLiteralAnnotation(final Class<?> clazz, final Class<A> annotation) {
		return clazz.getAnnotation(annotation);
	}

	/**
	 * Returns a flag indicating whether or not the Class literal has the annotation.
	 */
	public static <A extends Annotation> boolean literalHasAnnotation(final Class<?> clazz, final Class<A> annotation) {
		return clazz.getAnnotation(annotation) != null;
	}

	/**
	 * Returns a flag indicating whether or not the Class has the annotation.
	 */
	public static <A extends Annotation> boolean literalHasAnnotation(final String clazz, final Class<A> annotation) {
		return literalHasAnnotation(LiteralTool.getLiteral(clazz), annotation);
	}

	/**
	 * Returns a flag indicating whether or not the field on the Class has the annotation.
	 */
	public static <A extends Annotation> boolean fieldHasAnnotation(final Class<?> clazz, final String field,
			final Class<A> annotation) {
		return FieldTool.getField(clazz, field).getAnnotation(annotation) != null;
	}

	/**
	 * Returns a flag indicating whether or not the field on the Class has the annotation.
	 */
	public static <A extends Annotation> boolean fieldHasAnnotation(final String clazz, final String field,
			final Class<A> annotation) {
		return fieldHasAnnotation(LiteralTool.getLiteral(clazz), field, annotation);
	}

	/**
	 * Returns a flag indicating whether or not the method on the Class has the annotation.
	 */
	public static <A extends Annotation> boolean methodHasAnnotation(final String clazz, final String method,
			final Class<A> annotation) {
		return methodHasAnnotation(LiteralTool.getLiteral(clazz), method, annotation);
	}

	/**
	 * Returns a flag indicating whether or not the method on the Class has the annotation.
	 */
	public static <A extends Annotation> boolean methodHasAnnotation(final Class<?> clazz, final String method,
			final Class<A> annotation) {
		return MethodTool.getMethod(clazz, method).getAnnotation(annotation) != null;
	}

	/**
	 * Returns a flag indicating whether or not the method has the annotation.
	 */
	public static <A extends Annotation> boolean methodHasAnnotation(final Method method, final Class<A> annotation) {
		return method.getAnnotation(annotation) != null;
	}

	/**
	 * Lists all Fields on the Class with the annotation.
	 */
	public static <A extends Annotation> List<AnnotatedField<A>> getFieldsAnnotatedWith(final Class<?> clazz,
			final Class<A> annotation) {
		List<AnnotatedField<A>> result = NewCollection.list(NullPolicy.REJECT);

		for (Field field : FieldTool.getFields(clazz)) {
			if (field.getAnnotation(annotation) != null) {
				result.add(new AnnotatedField<A>(field, field.getAnnotation(annotation)));
			}
		}
		return result;
	}

	/**
	 * Lists all Fields on the Class with the annotation.
	 */
	public static <A extends Annotation> List<AnnotatedField<A>> getFieldsAnnotatedWith(final String clazz,
			final Class<A> annotation) {
		return getFieldsAnnotatedWith(LiteralTool.getLiteral(clazz), annotation);
	}

	/**
	 * Lists all Methods on the Class with the annotation.
	 */
	public static <A extends Annotation> List<AnnotatedMethod<A>> getMethodsAnnotatedWith(final Class<?> clazz,
			final Class<A> annotation) {

		List<AnnotatedMethod<A>> result = NewCollection.list(NullPolicy.REJECT);

		for (Method method : MethodTool.getAllMethods(clazz)) {
			if (method.getAnnotation(annotation) != null) {
				result.add(new AnnotatedMethod<A>(method, method.getAnnotation(annotation)));
			}
		}
		return result;
	}

	/**
	 * Lists all Methods on the Class with the annotation.
	 */
	public static <A extends Annotation> List<AnnotatedMethod<A>> getMethodsAnnotatedWith(final String clazz,
			final Class<A> annotation) {
		return getMethodsAnnotatedWith(LiteralTool.getLiteral(clazz), annotation);
	}

	public static <A extends Annotation> A getParameterAnnotation(Method m, int argument, Class<A> type) {
		Annotation[] annotations = m.getParameterAnnotations()[argument];
		for (Annotation annotation : annotations) {
			if (LiteralTool.isAssignable(annotation.getClass(), type)) {
				return (A) annotation;
			}
		}
		return null;
	}
}
