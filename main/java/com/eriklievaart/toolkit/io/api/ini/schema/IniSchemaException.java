package com.eriklievaart.toolkit.io.api.ini.schema;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class IniSchemaException extends RuntimeException {

	public IniSchemaException(String message, Object... args) {
		super(Str.sub(message, args));
	}
}