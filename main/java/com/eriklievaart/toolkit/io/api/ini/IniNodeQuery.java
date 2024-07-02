package com.eriklievaart.toolkit.io.api.ini;

import java.util.List;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.CollectionTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class IniNodeQuery {

	private List<IniNode> roots;

	public IniNodeQuery(List<IniNode> roots) {
		Check.notNull(roots);
		this.roots = roots;
	}

	/**
	 * Find a single IniNode matching the specified name. When a node matches, its children will not be recursed.
	 *
	 * @throws AssertionException
	 *             if there is more than one matching node
	 */
	public IniNode findNodeWithName(String name, int maxDepth) {
		return CollectionTool.getSingle(findNodesWithName(name, maxDepth));
	}

	/**
	 * Finds IniNodes matching the specified name. When a node matches, its children will not be recursed.
	 */
	public List<IniNode> findNodesWithName(String name, int maxDepth) {
		return findNodesWithName(roots, name, maxDepth);
	}

	static List<IniNode> findNodesWithName(List<IniNode> nodes, String name, int maxDepth) {
		List<IniNode> result = NewCollection.list();
		for (IniNode node : nodes) {

			if (node.getName().equals(name)) {
				result.add(node);

			} else if (maxDepth != 0) {
				result.addAll(findNodesWithName(node.getChildren(), name, maxDepth - 1));
			}
		}
		return result;
	}
}
