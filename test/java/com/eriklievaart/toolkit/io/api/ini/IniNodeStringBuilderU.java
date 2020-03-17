package com.eriklievaart.toolkit.io.api.ini;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class IniNodeStringBuilderU {

	@Test
	public void nodeWithNameOnly() {
		IniNode node = new IniNode("root");
		String string = new IniNodeStringBuilder().append(node).toString();
		Check.isEqual(string, "root\n");
	}

	@Test
	public void nodeWithNameAndId() {
		IniNode node = new IniNode("root", "foo");
		String string = new IniNodeStringBuilder().append(node).toString();
		Check.isEqual(string, "root[foo]\n");
	}

	@Test
	public void nodeWithProperty() {
		IniNode node = new IniNode("root");
		node.setProperty("prop", "val");
		String string = new IniNodeStringBuilder().append(node).toString();
		Check.isEqual(string, "root\n\tprop=val\n");
	}

	@Test
	public void nodeWithChild() {
		IniNode parent = new IniNode("parent");
		IniNode child = new IniNode("child");
		parent.addChild(child);

		String string = new IniNodeStringBuilder().append(parent).toString();
		Check.isEqual(string, "parent\n\tchild\n");
	}

	@Test
	public void complexNode() {
		IniNode parent = new IniNode("parent");
		parent.setProperty("a", "b");

		IniNode child1 = new IniNode("child1");
		child1.setProperty("c", "d");
		parent.addChild(child1);

		IniNode child2 = new IniNode("child2");
		child2.setProperty("e", "f");
		parent.addChild(child2);

		String string = new IniNodeStringBuilder().append(parent).toString();
		Check.isEqual(string, "parent\n\ta=b\n\tchild1\n\t\tc=d\n\tchild2\n\t\te=f\n");
	}
}