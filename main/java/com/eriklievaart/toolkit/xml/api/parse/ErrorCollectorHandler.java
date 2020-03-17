package com.eriklievaart.toolkit.xml.api.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ErrorCollectorHandler implements ErrorHandler {

	private final List<SaxParseExceptionWrapper> collect = new ArrayList<>();

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		throw exception; // better stop
	}

	@Override
	public void error(SAXParseException e) throws SAXParseException {
		collect.add(new SaxParseExceptionWrapper(e, SaxParseExceptionWrapper.ERROR));
	}

	@Override
	public void warning(SAXParseException e) throws SAXParseException {
		collect.add(new SaxParseExceptionWrapper(e, SaxParseExceptionWrapper.WARNING));
	}

	public List<SaxParseExceptionWrapper> getExceptions() {
		return Collections.unmodifiableList(collect);
	}
}