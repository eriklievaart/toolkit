package com.eriklievaart.toolkit.xml.api;

import com.eriklievaart.toolkit.lang.api.check.Check;

class SaxReflectionInstructions {

	private final String literalTarget;
	private final String popTarget;

	public SaxReflectionInstructions(final String prefix) {
		Check.notNull("Cannot create SaxInstructions for <null>");

		literalTarget = prefix + ".element";
		popTarget = prefix + ".close";
	}

	public boolean isLiteralTarget(final String target) {
		return literalTarget.equals(target);
	}

	public boolean isPopTarget(final String target) {
		return popTarget.equals(target);
	}
}
