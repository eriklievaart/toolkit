package com.eriklievaart.toolkit.io.api.ini;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class IniNodeQueryU {

	@Test
	public void findNodesWithNameRootsOnly() {
		List<IniNode> nodes = NewCollection.list();

		nodes.add(new IniNode("alpha"));
		nodes.add(new IniNode("beta"));
		nodes.add(new IniNode("alpha"));

		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "alpha", 0)).hasSize(2);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "beta", 0)).hasSize(1);
	}

	@Test
	public void findNodesWithNameRecursive() {

		IniNode root = new IniNode("root");
		root.createPath("leaf");
		root.createPath("branch/leaf");
		root.createPath("branch/branch/leaf");
		List<IniNode> nodes = Arrays.asList(root);

		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "leaf", -1)).hasSize(3);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "leaf", 0)).hasSize(0);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "leaf", 1)).hasSize(1);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "leaf", 2)).hasSize(2);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "leaf", 3)).hasSize(3);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "leaf", 4)).hasSize(3);

		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "branch", -1)).hasSize(1);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "branch", 0)).hasSize(0);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "branch", 1)).hasSize(1);
		Assertions.assertThat(IniNodeQuery.findNodesWithName(nodes, "branch", 2)).hasSize(1);
	}
}
