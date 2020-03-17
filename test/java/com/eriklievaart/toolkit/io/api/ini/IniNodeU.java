package com.eriklievaart.toolkit.io.api.ini;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.mock.BombSquad;

public class IniNodeU {

	@Test
	public void getChildRoot() {
		IniNode rootNode = new IniNode("parent");
		IniNode childNode = new IniNode("child");
		IniNode nestedNode = new IniNode("nested");
		rootNode.addChild(childNode);
		childNode.addChild(nestedNode);

		IniNode actual = rootNode.getChild("child");
		Check.isEqual(actual, childNode);
	}

	@Test
	public void getChildNested() {
		IniNode rootNode = new IniNode("parent");
		IniNode childNode = new IniNode("child");
		IniNode nestedNode = new IniNode("nested");
		rootNode.addChild(childNode);
		childNode.addChild(nestedNode);

		IniNode actual = rootNode.getChild("child/nested");
		Check.isEqual(actual, nestedNode);
	}

	@Test(expected = AssertionException.class)
	public void getChildMissing() {
		IniNode rootNode = new IniNode("parent");
		IniNode childNode = new IniNode("child");
		IniNode nestedNode = new IniNode("nested");
		rootNode.addChild(childNode);
		childNode.addChild(nestedNode);

		rootNode.getChild("child/other");
	}

	@Test
	public void hasChildTests() {
		IniNode rootNode = new IniNode("parent");
		IniNode childNode = new IniNode("child");
		IniNode nestedNode = new IniNode("nested");
		rootNode.addChild(childNode);
		childNode.addChild(nestedNode);

		Check.isTrue(rootNode.hasChild("child"));
		Check.isTrue(rootNode.hasChild("child/nested"));
		Check.isFalse(rootNode.hasChild("other"));
		Check.isFalse(rootNode.hasChild("child/other"));
	}

	@Test
	public void hasPropertyPathRoot() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");

		Check.isTrue(rootNode.hasProperty("type"));
		Check.isFalse(rootNode.hasProperty("pear"));
	}

	@Test
	public void hasPropertyPathNested() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");

		IniNode propertiesNode = new IniNode("properties");
		propertiesNode.setProperty("name", "myid");
		propertiesNode.setProperty("priority", "3");
		rootNode.addChild(propertiesNode);

		Check.isTrue(rootNode.hasProperty("type"));
		Check.isTrue(rootNode.hasProperty("properties/priority"));
		Check.isFalse(rootNode.hasProperty("pear"));
		Check.isFalse(rootNode.hasProperty("properties/pear"));
	}

	@Test
	public void getPropertyPathRoot() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");

		Check.isEqual(rootNode.getProperty("type").toString(), "java.lang.Thread");
	}

	@Test
	public void getPropertyPathNested() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");

		IniNode propertiesNode = new IniNode("properties");
		propertiesNode.setProperty("name", "myid");
		propertiesNode.setProperty("priority", "3");
		rootNode.addChild(propertiesNode);

		Check.isEqual(rootNode.getProperty("properties/priority"), "3");
	}

	@Test
	public void getPropertyPathNestedMissing() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");
		BombSquad.diffuse("missing property", () -> rootNode.getProperty("properties/priority"));
	}

	@Test
	public void getPropertyOrDefault() {
		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");

		Check.isEqual(rootNode.getPropertyOrDefault("type", "ignore").toString(), "java.lang.Thread");
		Check.isEqual(rootNode.getPropertyOrDefault("fallback", "java.util.Vector").toString(), "java.util.Vector");
	}

	@Test
	public void ifPropertyPass() {
		AtomicReference<String> result = new AtomicReference<>("changeme");

		IniNode rootNode = new IniNode("thread");
		rootNode.setProperty("type", "java.lang.Thread");
		rootNode.ifProperty("type", value -> result.set(value));

		Check.isEqual(result.get(), "java.lang.Thread");
	}

	@Test
	public void ifPropertyFail() {
		AtomicReference<String> result = new AtomicReference<>("ignore");

		IniNode rootNode = new IniNode("thread");
		rootNode.ifProperty("type", value -> result.set(value));

		Check.isEqual(result.get(), "ignore");
	}

	@Test
	public void createPath() {
		IniNode rootNode = new IniNode("parent");
		IniNode nested = rootNode.createPath("child/nested");

		Check.isTrue(rootNode.hasChild("child"));
		Check.isTrue(rootNode.hasChild("child/nested"));
		Check.isFalse(rootNode.hasChild("nested"));
		Check.isEqual(nested.getName(), "nested");

		Check.isTrue(rootNode.getChild("child") == rootNode.createPath("child"));
		Check.isTrue(rootNode.getChild("child/nested") == nested);
	}
}