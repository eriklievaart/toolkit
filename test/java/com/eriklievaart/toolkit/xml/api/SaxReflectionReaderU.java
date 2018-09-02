package com.eriklievaart.toolkit.xml.api;

import java.awt.Component;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.eriklievaart.toolkit.convert.api.Converter;
import com.eriklievaart.toolkit.convert.api.Converters;
import com.eriklievaart.toolkit.convert.api.construct.IntegerConstructor;
import com.eriklievaart.toolkit.convert.api.construct.StringConstructor;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.xml.api.SaxReflectionReader;

public class SaxReflectionReaderU {

	private final String xml;

	{
		StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		builder.append("<?prefix.element panel=javax.swing.JPanel?>");
		builder.append("<?prefix.element button=javax.swing.JButton?>");
		builder.append("<?prefix.close add(java.awt.Component)?>");

		builder.append("<panel>");
		builder.append("<button text=\"label\" horizontalTextPosition=\"4\" />");
		builder.append("</panel>");
		xml = builder.toString().replaceAll(">", ">\n");
	}

	@Test
	public void readXml() throws IOException, SAXException {
		Converter<?> strings = new StringConstructor().createConverter();
		Converter<?> ints = new IntegerConstructor().createConverter();
		SaxReflectionReader reader = new SaxReflectionReader("prefix", new Converters(strings, ints));
		JPanel panel = reader.readXmlFile(new StringReader(xml));

		int count = panel.getComponentCount();
		Check.isEqual(count, 1, "Button child was not created!");

		Component component = panel.getComponent(0);
		Check.isInstance(JButton.class, component);

		JButton button = (JButton) component;
		Check.isEqual(button.getText(), "label");
		Check.isEqual(button.getHorizontalTextPosition(), 4);
	}
}
