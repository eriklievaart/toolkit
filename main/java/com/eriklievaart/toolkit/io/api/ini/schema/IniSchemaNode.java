package com.eriklievaart.toolkit.io.api.ini.schema;

import com.eriklievaart.toolkit.io.api.ini.IniNode;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class IniSchemaNode {

	private String name;
	private boolean required = false;
	private boolean multiple = false;
	private boolean found = false;

	public IniSchemaNode(String name, IniSchemaMulitplicity multiplicity) {
		this.name = name;
		Check.matches(name, IniNode.IDENTIFIER_REGEX);

		switch (multiplicity) {
		case OPTIONAL:
			return;

		case ANY:
			multiple = true;
			return;

		case REQUIRED:
			required = true;
			return;

		default:
			throw new FormattedException("Unknown enum constant %", multiplicity);
		}
	}

	public boolean match(IniNode node) {
		if (!node.getName().equals(name)) {
			return false;
		}
		if (required && found && !multiple) {
			throw new AssertionException("Duplicate node % => $; schema specifies max 1", name, node);
		}
		found = true;
		return true;
	}

	public boolean isNotSatisfied() {
		return required && !found;
	}

	@Override
	public String toString() {
		return ToString.simple(this, "${$}", name);
	}
}
