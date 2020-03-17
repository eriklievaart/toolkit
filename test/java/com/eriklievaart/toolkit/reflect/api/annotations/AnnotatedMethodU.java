package com.eriklievaart.toolkit.reflect.api.annotations;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.concurrent.Immutable;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedMethod;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;
import com.eriklievaart.toolkit.swing.api.menu.Command;

public class AnnotatedMethodU {

	@Test
	public void getAnnotation() {
		class Annotated {
			@Immutable
			@Command(name = "guice")
			public void dummy() {
			}
		}
		AnnotatedMethod<Immutable> method = AnnotationTool.getMethodsAnnotatedWith(Annotated.class, Immutable.class)
				.iterator().next();

		Check.notNull(method);

		Command command = method.getAnnotation(Command.class);
		Check.notNull(command);
		CheckStr.isEqual(command.name(), "guice");
	}
}