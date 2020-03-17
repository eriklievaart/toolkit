package com.eriklievaart.toolkit.lang.api.tree;

import com.eriklievaart.toolkit.lang.api.ToString;

public class RootNode<E extends Comparable<E>> extends AbstractNode<E> {

	@Override
	public String toString() {
		return ToString.simple(this, "$[$]", children.size());
	}
}