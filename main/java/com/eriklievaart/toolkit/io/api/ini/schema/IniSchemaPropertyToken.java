package com.eriklievaart.toolkit.io.api.ini.schema;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.FromCollection;

public class IniSchemaPropertyToken {

	private IniSchemaPropertyEnum type;
	private String value;

	public IniSchemaPropertyToken(IniSchemaPropertyEnum type) {
		Check.notNull(type);
		this.type = type;
	}

	public IniSchemaPropertyToken(IniSchemaPropertyEnum type, String value) {
		Check.notNull(type);
		Check.notBlank(value);
		this.type = type;
		this.value = value.trim();
	}

	public IniSchemaPropertyEnum getType() {
		return type;
	}

	public String getValueAsString() {
		return value;
	}

	public List<String> getValueAsList() {
		if (value == null) {
			return Collections.emptyList();
		}
		return FromCollection.toList(value.split("[ ,]++"));
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$]", type);
	}

}
