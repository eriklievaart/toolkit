package com.eriklievaart.toolkit.lang.api.tree;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public abstract class AbstractNode<E extends Comparable<E>> {

	protected List<SortedNode<E>> children = NewCollection.concurrentList();

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public List<SortedNode<E>> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public void addChild(SortedNode<E> child) {
		Check.notNull(child);
		children.add(child);
	}

	public SortedNode<E> createChild(E value) {
		SortedNode<E> child = new SortedNode<>(value);
		children.add(child);

		Collections.sort(children);
		return child;
	}
}