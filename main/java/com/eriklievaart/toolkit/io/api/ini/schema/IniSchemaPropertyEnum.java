package com.eriklievaart.toolkit.io.api.ini.schema;

import java.util.Set;

import com.eriklievaart.toolkit.io.api.ini.IniNode;

public enum IniSchemaPropertyEnum {

	NONE {
		@Override
		public void verifyProperty(IniNode node, IniSchemaPropertyToken token) throws IniSchemaException {
			Set<String> properties = node.getPropertyNames();
			if (!properties.isEmpty()) {
				throw new IniSchemaException("$ found %, no properties allowed", node, properties);
			}
		}
	},
	ANY {
		@Override
		public void verifyProperty(IniNode node, IniSchemaPropertyToken token) throws IniSchemaException {
		}
	},
	REGEX {
		@Override
		public void verifyProperty(IniNode node, IniSchemaPropertyToken token) throws IniSchemaException {
			String regex = token.getValueAsString();
			for (String property : node.getPropertyNames()) {
				if (!property.matches(regex)) {
					throw new IniSchemaException("$ property % does not match regex %", node, property, regex);
				}
			}
		}
	},
	EXACT {
		@Override
		public void verifyProperty(IniNode node, IniSchemaPropertyToken token) throws IniSchemaException {
			for (String property : node.getPropertyNames()) {
				if (!token.getValueAsList().contains(property)) {
					throw new IniSchemaException("$ property % not in %", node, property, token.getValueAsString());
				}
			}
		}
	};

	public abstract void verifyProperty(IniNode contentNode, IniSchemaPropertyToken token) throws IniSchemaException;
}