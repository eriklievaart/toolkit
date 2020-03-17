package com.eriklievaart.toolkit.swing.api.menu;

import java.util.List;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedMethod;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;

public class ReflectionActionBuilder {

	private ReflectionActionBuilder() {
	}

	public static List<NamedAction> createActions(Object object) {
		Check.notNull(object);
		Class<?> literal = object.getClass();
		List<NamedAction> result = NewCollection.list();

		List<AnnotatedMethod<Command>> commands = AnnotationTool.getMethodsAnnotatedWith(literal, Command.class);
		if (commands.isEmpty()) {
			throw new ReflectException("No methods with @Command for " + literal);
		}

		for (AnnotatedMethod<Command> method : commands) {
			NamedAction action = createAction(method, object);
			result.add(action);
		}
		return result;
	}

	public static NamedAction createSingleAction(Object object) {
		List<NamedAction> actions = createActions(object);
		CheckCollection.isSize(actions, 1);
		return actions.iterator().next();
	}

	public static NamedAction createAction(Object object, String id) {
		for (NamedAction action : createActions(object)) {
			if (action.getName().equalsIgnoreCase(id)) {
				return action;
			}
		}
		throw new AssertionException("Action not found %", id);
	}

	private static NamedAction createAction(AnnotatedMethod<Command> method, Object object) {
		MethodWrapper wrapper = method.getMethodWrapper(object);
		Check.isEqual(wrapper.getArgumentTypes().length, 0, "Method % zero arguments expected", method.getName());
		NamedAction action = new NamedAction(method.getAnnotation().name(), method.getMethodWrapper(object));
		Accelerator accelerator = wrapper.getMethod().getAnnotation(Accelerator.class);
		if (accelerator != null) {
			action.setAccelerator(accelerator.value());
		}
		return action;
	}
}