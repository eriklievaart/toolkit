package com.eriklievaart.toolkit.lang.api.pattern;

import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;

public class PatternToolU {

	@Test
	public void replaceAll() {
		Check.isEqual(PatternTool.replaceAll("\\d", "", "test123"), "test");
	}

	@Test
	public void getGroups() {
		String input = "\n@/";
		List<String> groups = PatternTool.getGroups("(.*)@(.*)", input);

		CheckCollection.notEmpty(groups);
		Check.isEqual(groups.get(0), input);
		Check.isEqual(groups.get(1), "\n");
		Check.isEqual(groups.get(2), "/");
	}

	@Test
	public void getGroupsWithNull() {
		String input = "a@";
		List<String> groups = PatternTool.getGroups("(.*)@(\\w++)?", input);

		CheckCollection.notEmpty(groups);
		Check.isEqual(groups.get(0), input);
		Check.isEqual(groups.get(1), "a");
		Check.isEqual(groups.get(2), null);
	}
}