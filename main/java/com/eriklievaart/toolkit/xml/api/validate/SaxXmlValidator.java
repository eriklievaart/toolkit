package com.eriklievaart.toolkit.xml.api.validate;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.SAXParseException;

public class SaxXmlValidator {

	public static List<SaxParseExceptionWrapper> validateXML(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			ErrorCollectorHandler collect = new ErrorCollectorHandler();
			builder.setErrorHandler(collect);
			builder.parse(is);
			return collect.getExceptions();

		} catch (SAXParseException e) {
			return Arrays.asList(new SaxParseExceptionWrapper(e, SaxParseExceptionWrapper.FATAL));

		} catch (Exception e) {
			throw new Error("Fatal error when parsing XML", e);
		}
	}
}