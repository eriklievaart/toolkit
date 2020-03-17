package com.eriklievaart.toolkit.swing.api;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.AbstractAction;

public class ActionLambda extends AbstractAction {

	private Consumer<ActionEvent> consumer;

	public ActionLambda(Consumer<ActionEvent> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		consumer.accept(ae);
	}
}