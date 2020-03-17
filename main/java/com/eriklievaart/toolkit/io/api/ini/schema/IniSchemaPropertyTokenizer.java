package com.eriklievaart.toolkit.io.api.ini.schema;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class IniSchemaPropertyTokenizer {

	public static IniSchemaPropertyToken parse(String input) {
		Check.matches(input, "\\w++(?::.*+)?");
		if (input.trim().matches("any:?")) {
			return new IniSchemaPropertyToken(IniSchemaPropertyEnum.ANY);
		}
		String[] split = input.split(":", 2);
		IniSchemaPropertyEnum type = IniSchemaPropertyEnum.valueOf(split[0].toUpperCase());
		return new IniSchemaPropertyToken(type, split[1]);
	}
}