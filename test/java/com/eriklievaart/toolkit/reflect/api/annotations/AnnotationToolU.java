package com.eriklievaart.toolkit.reflect.api.annotations;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;
import com.eriklievaart.toolkit.lang.api.concurrent.Stateless;
import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;

@Stateless
public class AnnotationToolU {

	private final String CLAZZ = getClass().getName();

	@ThreadSafe
	private String dummy;

	public AnnotationToolU() {
	}

	@Test
	public void getClassAnnotationSuccess() {
		Check.notNull(AnnotationTool.getLiteralAnnotation(CLAZZ, Stateless.class));
	}

	@Test
	public void getClassAnnotationFailure() {
		Check.isNull(AnnotationTool.getLiteralAnnotation(CLAZZ, Immutable.class));
	}

	@Test
	public void getFieldsAnnotatedWithEmpty() {
		Check.isEqual(AnnotationTool.getFieldsAnnotatedWith(CLAZZ, Stateless.class).size(), 0);
	}

	@Test
	public void getFieldsAnnotatedWithSingle() {
		Check.isEqual(AnnotationTool.getFieldsAnnotatedWith(CLAZZ, ThreadSafe.class).size(), 1);
	}

	@Test
	public void fieldHasAnnotationSuccess() {
		Check.isTrue(AnnotationTool.fieldHasAnnotation(CLAZZ, "dummy", ThreadSafe.class));
	}

	@Test
	public void fieldHasAnnotationFailure() {
		Check.isFalse(AnnotationTool.fieldHasAnnotation(CLAZZ, "dummy", Immutable.class));
	}

	@Test
	public void methodHasAnnotationSuccess() {
		Check.isTrue(AnnotationTool.methodHasAnnotation(CLAZZ, "methodHasAnnotationSuccess", Test.class));
	}

	@Test
	public void methodHasAnnotationFailure() {
		Check.isFalse(AnnotationTool.methodHasAnnotation(CLAZZ, "methodHasAnnotationFailure", Immutable.class));
	}

	@Test
	@SuppressWarnings("unused")
	public void getMethodsAnnotatedWithNone() {
		class Annotated {
			public void single() {
			}
		}
		Check.isEqual(AnnotationTool.getMethodsAnnotatedWith(Annotated.class, Immutable.class).size(), 0);
	}

	@Test
	public void getMethodsAnnotatedWithSingle() {
		class Annotated {
			@Immutable
			public void single() {
			}
		}
		Check.isEqual(AnnotationTool.getMethodsAnnotatedWith(Annotated.class, Immutable.class).size(), 1);
	}
}
