package com.eriklievaart.toolkit.xml.api;

import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.eriklievaart.toolkit.convert.api.Converters;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.concurrent.RaceCondition;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;


@RaceCondition("This class is absolutely not Thread safe!")
class SaxReflectionHandler extends DefaultHandler {

	private final Stack<Object> hierarchy = NewCollection.stack();
	private final SaxReflectionInstructions instructions;
	private final SaxReflection reflection;
	private Object result;

	private final LogTemplate log = new LogTemplate(getClass());

	public SaxReflectionHandler(final String processingPrefix, final Converters converters) {
		instructions = new SaxReflectionInstructions(processingPrefix);
		reflection = new SaxReflection(converters);
	}

	@Override
	public void startElement(final String uri, final String element, final String full, final Attributes attributes)
			throws SAXException {
		reflection.checkElementMapped(element);

		Map<String, Object> properties = NewCollection.map();
		for (int i = 0; i < attributes.getLength(); i++) {
			String property = attributes.getLocalName(i);
			properties.put(property, reflection.createProperty(element, property, attributes.getValue(i)));
		}
		hierarchy.add(reflection.createObject(element, properties));
	}

	@Override
	public void processingInstruction(final String target, final String data) throws SAXException {
		if (instructions.isLiteralTarget(target)) {
			String[] elementWithClass = PatternTool.split("=", data);
			reflection.putElementMapping(elementWithClass[0], elementWithClass[1]);
		}
		if (instructions.isPopTarget(target)) {
			reflection.setPopMethod(data);
		}
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		Object popped = hierarchy.pop();
		if (hierarchy.isEmpty()) {
			result = popped;
			log.trace("% result %", getClass().getSimpleName(), result);
			return;
		}
		reflection.callPopMethod(hierarchy.peek(), popped);
	}

	public Object getResult() {
		return result;
	}

}
