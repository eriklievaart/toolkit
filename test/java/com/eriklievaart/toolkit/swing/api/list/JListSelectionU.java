package com.eriklievaart.toolkit.swing.api.list;

import javax.swing.JList;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.swing.api.list.JListSelection;

public class JListSelectionU {

	@Test
	public void isListEmpty() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isTrue(selection.isListEmpty());

		list.setListData(new String[] { "" });
		Check.isFalse(selection.isListEmpty());
	}

	@Test
	public void getFirstSelectedOrLastInList() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isFalse(selection.getFirstSelectedOrLastInList().isPresent());

		list.setListData(new String[] { "one", "two", "three" });
		CheckStr.isEqual(selection.getFirstSelectedOrLastInList().get(), "three");

		list.setSelectedIndex(1);
		CheckStr.isEqual(selection.getFirstSelectedOrLastInList().get(), "two");
	}

	@Test
	public void getLastInList() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isFalse(selection.getLastInList().isPresent());

		list.setListData(new String[] { "one", "two", "three" });
		CheckStr.isEqual(selection.getLastInList().get(), "three");
	}

	@Test
	public void getFirstSelected() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isFalse(selection.getFirstSelected().isPresent());

		list.setListData(new String[] { "one", "two", "three" });
		list.setSelectedIndices(new int[] { 1, 2 });
		CheckStr.isEqual(selection.getFirstSelected().get(), "two");

		list.setSelectedIndex(2);
		CheckStr.isEqual(selection.getFirstSelected().get(), "three");
	}

	@Test
	public void getLastSelected() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isFalse(selection.getLastSelected().isPresent());

		list.setListData(new String[] { "one", "two", "three" });
		list.setSelectedIndices(new int[] { 0, 1 });
		CheckStr.isEqual(selection.getLastSelected().get(), "two");

		list.setSelectedIndex(0);
		CheckStr.isEqual(selection.getLastSelected().get(), "one");
	}

	@Test
	public void getSelectionCount() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isEqual(selection.getSelectionCount(), 0);

		list.setListData(new String[] { "one", "two", "three" });
		Check.isEqual(selection.getSelectionCount(), 0);

		list.setSelectedIndices(new int[] { 0, 1 });
		Check.isEqual(selection.getSelectionCount(), 2);
	}

	@Test
	public void getSelectionAtLeast() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);
		Check.isTrue(selection.selectedAtLeast(0));
		Check.isFalse(selection.selectedAtLeast(1));

		list.setListData(new String[] { "one", "two", "three" });
		list.setSelectedIndices(new int[] { 0, 1 });
		Check.isTrue(selection.selectedAtLeast(0));
		Check.isTrue(selection.selectedAtLeast(1));
		Check.isTrue(selection.selectedAtLeast(2));
		Check.isFalse(selection.selectedAtLeast(3));
	}

	@Test
	public void getSelectionAtMost() {
		JList<String> list = new JList<>();
		JListSelection<String> selection = JListSelection.of(list);

		list.setListData(new String[] { "one", "two", "three" });
		Check.isTrue(selection.selectedAtMost(0));
		Check.isTrue(selection.selectedAtMost(1));

		list.setSelectedIndices(new int[] { 0, 1 });
		Check.isFalse(selection.selectedAtMost(0));
		Check.isFalse(selection.selectedAtMost(1));
		Check.isTrue(selection.selectedAtMost(2));
		Check.isTrue(selection.selectedAtMost(3));
	}

}
