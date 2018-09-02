package com.eriklievaart.toolkit.io.api.ini.schema;

import com.eriklievaart.toolkit.lang.api.ToString;

public class IniSchemaToken {

	private final String raw;
	private final IniSchemaMulitplicity multiplicity;

	public IniSchemaToken(String raw, IniSchemaMulitplicity multiplicity) {
		this.raw = raw;
		this.multiplicity = multiplicity;
	}

	public String getRaw() {
		return raw;
	}

	public IniSchemaMulitplicity getMultiplicity() {
		return multiplicity;
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$]", raw);
	}
}
