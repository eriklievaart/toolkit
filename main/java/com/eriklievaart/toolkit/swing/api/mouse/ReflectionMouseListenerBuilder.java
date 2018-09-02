package com.eriklievaart.toolkit.swing.api.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.NullPolicy;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotatedMethod;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;

public class ReflectionMouseListenerBuilder {

	private ReflectionMouseListenerBuilder() {
	}

	public static List<MouseListener> createMouseListeners(Object object) {
		Check.notNull(object);
		Class<?> literal = object.getClass();

		List<AnnotatedMethod<Mouse>> methods = AnnotationTool.getMethodsAnnotatedWith(literal, Mouse.class);
		if (methods.isEmpty()) {
			throw new ReflectException("No methods with @Mouse for " + literal);
		}

		List<MouseListener> result = NewCollection.list(NullPolicy.REJECT);
		for (AnnotatedMethod<Mouse> method : methods) {
			final Mouse mouse = method.getAnnotation();
			final MethodWrapper wrapper = method.getMethodWrapper(object);
			Check.isEqual(wrapper.getArgumentTypes().length, 1, "Expecting a MouseEvent as argument.");

			result.add(createMouseListener(mouse, wrapper));
		}
		return result;
	}

	private static MouseListener createMouseListener(final Mouse mouse, final MethodWrapper wrapper) {
		MouseListener proxy = LiteralTool.createProxy(MouseListener.class, new InvocationHandler() {
			@Override
			public Object invoke(Object o, Method method, Object[] args) {
				if (args.length != 1 || !(args[0] instanceof MouseEvent)) {
					return null;
				}
				MouseEvent event = (MouseEvent) args[0];
				if (mouse.type().getId() != event.getID()) {
					return null;
				}

				return wrapper.invoke(args);
			}
		});
		return proxy;
	}
}
