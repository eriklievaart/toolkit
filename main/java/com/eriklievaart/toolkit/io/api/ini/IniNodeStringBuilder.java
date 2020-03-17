package com.eriklievaart.toolkit.io.api.ini;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class IniNodeStringBuilder {

	private static final String NEWLINE = "\n";
	private static final String TAB = "\t";
	private StringBuilder builder = new StringBuilder();

	@Override
	public String toString() {
		return builder.toString();
	}

	public IniNodeStringBuilder append(IniNode node) {
		append(node, 0);
		return this;
	}

	private void append(IniNode node, int nodeTabs) {
		appendTabs(nodeTabs);
		builder.append(node.getName());
		appendIdentifier(node);
		builder.append(NEWLINE);

		node.getPropertiesMap().forEach((name, value) -> {
			appendTabs(nodeTabs + 1);
			builder.append(name);
			builder.append("=");
			builder.append(value);
			builder.append(NEWLINE);
		});
		for (IniNode child : node.getChildren()) {
			append(child, nodeTabs + 1);
		}
	}

	private void appendIdentifier(IniNode node) {
		if (!Str.isBlank(node.getIdentifier())) {
			builder.append("[").append(node.getIdentifier()).append("]");
		}
	}

	private void appendTabs(int tabs) {
		for (int i = 0; i < tabs; i++) {
			builder.append(TAB);
		}
	}
}