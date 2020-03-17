package com.eriklievaart.toolkit.xml.api;

import java.util.Map;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.convert.api.Converters;
import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;
import com.eriklievaart.toolkit.reflect.api.PropertyTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.api.method.PropertyWrapper;

class SaxReflection {

	private final LogTemplate log = new LogTemplate(getClass());
	private final Map<String, Class<?>> elementMapping = NewCollection.map();
	private final Converters converters;
	private String popMethod;

	public SaxReflection(final Converters converters) {
		this.converters = converters;
	}

	public void putElementMapping(final String element, final String literal) {
		log.debug("Creating instances of % for % elements", literal, element);
		elementMapping.put(element, LiteralTool.getLiteral(literal));
	}

	public void checkElementMapped(final String element) {
		ReflectException.unless(elementMapping.containsKey(element), "No mapping for %", element);
	}

	public Object createProperty(final String element, final String property, final String value) {
		PropertyWrapper descriptor = PropertyTool.getProperty(getLiteral(element), property);
		Check.notNull(descriptor, "Missing property % on %", property, getLiteral(element));

		try {
			return converters.to(descriptor.getType(), value);
		} catch (ConversionException e) {
			throw new FormattedException("% cannot be converted to % for %", value, descriptor.getType(), property);
		}
	}

	public Object createObject(final String element, final Map<String, Object> properties) {
		log.trace("Creating bean % with properties %", getLiteral(element), properties);
		return LiteralTool.newPopulatedInstance(getLiteral(element), properties);
	}

	private Class<?> getLiteral(final String element) {
		return elementMapping.get(element);
	}

	public void setPopMethod(final String method) {
		log.debug("Calling method % on parent when closing elements", method);
		this.popMethod = method;
	}

	public void callPopMethod(final Object parent, final Object child) {
		String method = PatternTool.getGroup1("([^(]++).*+", popMethod);
		String argument = PatternTool.getGroup1("[^(]++\\(([^)]*+)\\)", popMethod);

		InstanceTool.getMethodWrapper(method, parent, argument).invoke(child);
	}
}