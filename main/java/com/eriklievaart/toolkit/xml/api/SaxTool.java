package com.eriklievaart.toolkit.xml.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.eriklievaart.toolkit.io.api.StreamTool;

public class SaxTool {

	public static void parse(File file, DefaultHandler handler) throws IOException {
		try {
			getParser().parse(file, handler);

		} catch (SAXException | ParserConfigurationException e) {
			throw new IOException(e);
		}
	}

	public static void parse(InputStream is, DefaultHandler handler) throws IOException {
		try {
			getParser().parse(is, handler);

		} catch (SAXException | ParserConfigurationException e) {
			throw new IOException(e);
		}
	}

	public static void parse(String xml, DefaultHandler handler) throws IOException {
		parse(StreamTool.toInputStream(xml), handler);
	}

	private static SAXParser getParser() throws SAXException, ParserConfigurationException {
		return SAXParserFactory.newInstance().newSAXParser();
	}
}
