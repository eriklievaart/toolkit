package com.eriklievaart.toolkit.swing.api.list;

import java.util.Optional;

import javax.swing.JList;
import javax.swing.ListModel;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class JListSelection<E> {

	private JList<E> list;

	public JListSelection() {
		this(new JList<>());
	}

	public JListSelection(JList<E> list) {
		Check.notNull(list);
		this.list = list;
	}

	public static <E> JListSelection<E> of(JList<E> list) {
		return new JListSelection<>(list);
	}

	public boolean isListEmpty() {
		return list.getModel().getSize() < 1;
	}

	public Optional<E> getFirstSelectedOrLastInList() {
		if (isListEmpty()) {
			return Optional.empty();
		}
		Optional<E> first = getFirstSelected();
		return first.isPresent() ? first : getLastInList();
	}

	public Optional<E> getLastInList() {
		if (isListEmpty()) {
			return Optional.empty();
		}
		ListModel<E> model = list.getModel();
		return Optional.of(model.getElementAt(model.getSize() - 1));
	}

	public Optional<E> getFirstSelected() {
		E value = list.getSelectedValue();
		return value == null ? Optional.empty() : Optional.of(value);
	}

	public Optional<E> getLastSelected() {
		int[] indices = list.getSelectedIndices();
		if (indices.length == 0) {
			return Optional.empty();
		}
		int lastIndex = indices[indices.length - 1];
		return Optional.of(list.getModel().getElementAt(lastIndex));
	}

	public int[] getSelectedIndices() {
		return list.getSelectedIndices();
	}

	public int getSelectionCount() {
		return getSelectedIndices().length;
	}

	public boolean selectedAtLeast(int count) {
		return getSelectionCount() >= count;
	}

	public boolean selectedAtMost(int count) {
		return getSelectionCount() <= count;
	}
}