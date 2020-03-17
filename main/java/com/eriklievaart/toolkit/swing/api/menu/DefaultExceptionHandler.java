package com.eriklievaart.toolkit.swing.api.menu;

import javax.swing.JOptionPane;

import com.eriklievaart.toolkit.lang.api.ThrowableTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class DefaultExceptionHandler implements ExceptionHandler {
	private LogTemplate log = new LogTemplate(getClass());

	@Override
	public void handle(Exception e) {
		Throwable cause = ThrowableTool.getRootCause(e);
		log.debug("Exception occured: $; dumping stack on trace.", cause.getMessage());
		log.trace("Full stack trace for Throwable in event.", e);
		JOptionPane.showMessageDialog(null, cause.getMessage());
	}
}