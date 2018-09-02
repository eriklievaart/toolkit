package com.eriklievaart.toolkit.xml.api.parse;

import org.xml.sax.SAXParseException;

public class SaxParseExceptionWrapper {

	public static final String ERROR = "error";
	public static final String WARNING = "warning";
	public static final String FATAL = "fatal";

	private final SAXParseException exception;
	private final String type;

	public SaxParseExceptionWrapper(SAXParseException e, String type) {
		this.exception = e;
		this.type = type;
	}

	public String getMessage() {
		return exception.getLineNumber() + ": " + type + " => " + exception.getMessage();
	}

	public SAXParseException getCause() {
		return exception;
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
