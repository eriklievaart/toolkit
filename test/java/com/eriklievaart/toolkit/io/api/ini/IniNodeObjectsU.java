package com.eriklievaart.toolkit.io.api.ini;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class IniNodeObjectsU {

	@Test
	public void newInstance() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");

		IniNode propertiesNode = new IniNode("properties");
		propertiesNode.setProperty("name", "myid");
		propertiesNode.setProperty("priority", "3");
		rootNode.addChild(propertiesNode);

		Thread thread = IniNodeObjects.newInstance(rootNode);
		Check.isEqual(thread.getName(), "myid");
		Check.isEqual(thread.getPriority(), 3);
	}
}