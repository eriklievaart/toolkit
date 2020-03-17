package com.eriklievaart.toolkit.io.api.ini;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.pattern.PatternTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class IniNodeParser {
	private static final String ASSIGNMENT = "=";

	private Stack<IniNode> stack = new Stack<IniNode>();
	private List<IniNode> nodes = new ArrayList<IniNode>();
	private int lineCount = 0;

	public List<IniNode> parse(List<String> lines) {
		stack.clear();
		nodes.clear();

		for (String line : lines) {
			lineCount++;
			if (Str.isBlank(line) || line.trim().startsWith("#")) {
				continue;
			}
			int tabs = countTabs(line);
			while (stack.size() > tabs) {
				stack.pop();
			}
			parseLine(line.substring(tabs), tabs > 0);
		}
		return nodes;
	}

	private void parseLine(String line, boolean child) {
		Check.isFalse(line.startsWith(" "), "No leading spaces allowed; " + line);
		if (line.contains(ASSIGNMENT)) {
			String[] nameValue = line.split(ASSIGNMENT, 2);
			stack.peek().setProperty(nameValue[0].trim(), nameValue[1].trim());
		} else {
			List<String> groups = PatternTool.getGroups("([a-zA-Z0-9_.]++)(?:\\[([a-zA-Z0-9_.]++)\\])?", line.trim());
			Check.isFalse(groups.isEmpty(), "Invalid ini object or property: $", line);
			IniNode node = new IniNode(groups.get(1), groups.get(2));
			node.setLineNumber(lineCount);
			if (child) {
				stack.peek().addChild(node);
			} else {
				nodes.add(node);
			}
			stack.push(node);
		}
	}

	static int countTabs(String line) {
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != '\t') {
				return i;
			}
		}
		return line.length();
	}
}