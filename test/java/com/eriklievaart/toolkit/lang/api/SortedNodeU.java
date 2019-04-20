package com.eriklievaart.toolkit.lang.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.tree.SortedNode;

public class SortedNodeU {

	@Test
	public void createChildSorted() {
		SortedNode<Integer> root = new SortedNode<>(0);
		root.createChild(5);
		root.createChild(3);
		root.createChild(4);

		Check.isEqual(root.getChildren().get(0).getValue(), 3);
		Check.isEqual(root.getChildren().get(1).getValue(), 4);
		Check.isEqual(root.getChildren().get(2).getValue(), 5);
	}

	@Test
	public void createChildBorderCaseLast() {
		SortedNode<Integer> root = new SortedNode<>(0);
		root.createChild(5);
		root.createChild(6);

		Check.isEqual(root.getChildren().get(0).getValue(), 5);
		Check.isEqual(root.getChildren().get(1).getValue(), 6);
	}

	@Test
	public void createChildBorderCaseFirst() {
		SortedNode<Integer> root = new SortedNode<>(0);
		root.createChild(6);
		root.createChild(5);

		Check.isEqual(root.getChildren().get(0).getValue(), 5);
		Check.isEqual(root.getChildren().get(1).getValue(), 6);
	}

	@Test
	public void isLeafPass() {
		SortedNode<Integer> root = new SortedNode<>(0);
		Check.isTrue(root.isLeaf());
	}

	@Test
	public void isLeafFail() {
		SortedNode<Integer> root = new SortedNode<>(0);
		root.createChild(5);
		Check.isFalse(root.isLeaf());
	}
}
