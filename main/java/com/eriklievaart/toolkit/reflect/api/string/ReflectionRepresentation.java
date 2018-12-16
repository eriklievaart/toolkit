package com.eriklievaart.toolkit.reflect.api.string;

import java.lang.reflect.Field;

import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.reflect.api.FieldTool;
import com.eriklievaart.toolkit.reflect.api.LiteralTool;

public class ReflectionRepresentation {

	private final String literalName;
	private final String memberName;

	public ReflectionRepresentation(String literalName, String memberName) {
		this.literalName = literalName;
		this.memberName = memberName;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getLiteralName() {
		return literalName;
	}

	public Class<?> getLiteral() {
		return LiteralTool.getLiteral(literalName);
	}

	public Field getField() {
		return FieldTool.getField(literalName, memberName);
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$#$]", literalName, memberName);
	}
}
