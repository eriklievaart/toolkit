package com.eriklievaart.toolkit.io.api.ini.schema;

import java.util.Arrays;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class IniSchemaPropertyTokenizerU {

	@Test
	public void simple() {
		IniSchemaPropertyToken property = IniSchemaPropertyTokenizer.parse("any");
		Check.isEqual(property.getType(), IniSchemaPropertyEnum.ANY);
	}

	@Test
	public void simpleWithColon() {
		IniSchemaPropertyToken property = IniSchemaPropertyTokenizer.parse("any:");
		Check.isEqual(property.getType(), IniSchemaPropertyEnum.ANY);
	}

	@Test
	public void value() {
		IniSchemaPropertyToken property = IniSchemaPropertyTokenizer.parse("exact: alpha beta ");
		Check.isEqual(property.getType(), IniSchemaPropertyEnum.EXACT);
		Check.isEqual(property.getValueAsString(), "alpha beta");
		Check.isEqual(property.getValueAsList(), Arrays.asList("alpha", "beta"));
	}
}
