package com.eriklievaart.toolkit.io.api.ini;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.test.api.Bomb;
import com.eriklievaart.toolkit.test.api.BombSquad;

public class IniNodeParserU {

	@Test
	public void countTabs() {
		Check.isEqual(IniNodeParser.countTabs(""), 0);
		Check.isEqual(IniNodeParser.countTabs("\t"), 1);
		Check.isEqual(IniNodeParser.countTabs("\t\t"), 2);
		Check.isEqual(IniNodeParser.countTabs("\t\t|"), 2);
	}

	@Test
	public void nodeWithNameOnly() {
		List<IniNode> nodes = new IniNodeParser().parse(Arrays.asList("root\n"));
		IniNode node = nodes.iterator().next();
		Check.isEqual(node.getName(), "root");
		Check.isNull(node.getIdentifier());
	}

	@Test
	public void invalidNode() {
		BombSquad.diffuse(AssertionException.class, "invalid", new Bomb() {
			@Override
			public void explode() throws Exception {
				new IniNodeParser().parse(Arrays.asList("$%\n"));
			}
		});
	}

	@Test
	public void nodeWithNameAndId() {
		List<IniNode> nodes = new IniNodeParser().parse(Arrays.asList("root[firefox]\n"));
		IniNode node = nodes.iterator().next();
		Check.isEqual(node.getName(), "root");
		Check.isEqual(node.getIdentifier(), "firefox");
	}

	@Test
	public void idWithDot() {
		List<IniNode> nodes = new IniNodeParser().parse(Arrays.asList("root[my.home]\n"));
		IniNode node = nodes.iterator().next();
		Check.isEqual(node.getName(), "root");
		Check.isEqual(node.getIdentifier(), "my.home");
	}

	@Test
	public void nodeWithProperty() {
		List<IniNode> nodes = new IniNodeParser().parse(Arrays.asList("root\n", "\tprop=val\n"));
		IniNode node = nodes.iterator().next();
		Check.isEqual(node.getName(), "root");
		Check.isEqual(node.getProperty("prop"), "val");
		Check.isNull(node.getIdentifier());
	}

	@Test
	public void nodeWithChild() {
		List<IniNode> nodes = new IniNodeParser().parse(Arrays.asList("root\n", "\tnested"));
		IniNode node = nodes.iterator().next();
		Check.isEqual(node.getName(), "root");
		Check.isNull(node.getIdentifier());

		IniNode child = node.getChildren().iterator().next();
		Check.isEqual(child.getName(), "nested");
	}

	@Test
	public void complexNode() {
		List<IniNode> nodes = new IniNodeParser().parse(Arrays.asList("root", "\ta=b", "\tnested[cid]", "\t\tc=d"));
		IniNode node = nodes.iterator().next();
		Check.isEqual(node.getName(), "root");
		Check.isNull(node.getIdentifier());
		Check.isEqual(node.getProperty("a"), "b");

		IniNode child = node.getChildren("nested").iterator().next();
		Check.isEqual(child.getName(), "nested");
		Check.isEqual(child.getIdentifier(), "cid");
		Check.isEqual(child.getProperty("c"), "d");
	}

}
