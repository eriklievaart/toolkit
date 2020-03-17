package com.eriklievaart.toolkit.reflect.api.string;

import java.util.List;

import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;

public class ReflectionRepresentationTool {

	public static ReflectionRepresentation fromString(String value) {
		ReflectException.on(value == null, "argument is <null>");
		List<String> groups = PatternTool.getGroups("\\s*+([^#\\s]++)#(\\S++)\\s*+", value);
		ReflectException.on(groups.isEmpty(), "% is not in the format [class]#[member]", value);
		return new ReflectionRepresentation(groups.get(1), groups.get(2));
	}
}