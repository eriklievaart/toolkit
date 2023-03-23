package com.eriklievaart.toolkit.xml.api;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class SaxToolU {

	@Test
	public void parse() throws IOException {
		String xml = "<root></root>";

		AtomicReference<String> text = new AtomicReference<>();
		SaxTool.parse(xml, new DefaultHandler() {
			@Override
			public void startElement(String uri, String local, String qName, Attributes a) throws SAXException {
				text.set(qName);
			}
		});
		Check.isEqual(text.get(), "root");
	}
}
