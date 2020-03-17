package com.eriklievaart.toolkit.reflect.api.annotations;

import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;
import com.eriklievaart.toolkit.lang.api.concurrent.Prototype;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedField;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;

public class AnnotatedFieldU {

	@Test
	public void getAnnotation() {
		class Annotated {
			@Immutable
			@Prototype
			private Object dummy;
		}
		List<AnnotatedField<Immutable>> fields = AnnotationTool
				.getFieldsAnnotatedWith(Annotated.class, Immutable.class);
		AnnotatedField<Immutable> field = fields.iterator().next();
		Check.notNull(field);

		Prototype command = field.getAnnotation(Prototype.class);
		Check.notNull(command);
	}
}