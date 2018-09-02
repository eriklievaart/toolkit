package com.eriklievaart.toolkit.swing.api.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;

public class NamedAction extends AbstractAction {
	private LogTemplate log = new LogTemplate(getClass());

	private final String name;
	private String accelerator;
	private MethodWrapper method;
	private ExceptionHandler handler = new DefaultExceptionHandler();

	public NamedAction(String name, MethodWrapper methodWrapper) {
		super(name);
		this.name = name;
		this.method = methodWrapper;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			log.trace("invoking named action %", name);
			method.invoke();
		} catch (Exception t) {
			handler.handle(t);
		}
	}

	public String getName() {
		return name;
	}

	public String getAccelerator() {
		return accelerator;
	}

	public void setAccelerator(String accelerator) {
		this.accelerator = accelerator;
	}

	public void setExceptionHandler(ExceptionHandler handler) {
		Check.notNull(handler);
		this.handler = handler;
	}
}
