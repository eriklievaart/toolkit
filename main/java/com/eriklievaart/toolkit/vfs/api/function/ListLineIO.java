package com.eriklievaart.toolkit.vfs.api.function;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class ListLineIO implements LineIO {

	private List<String> lines = NewCollection.list();

	@Override
	public void store(List<String> store) {
		lines.clear();
		lines.addAll(store);
	}

	@Override
	public List<String> load() {
		return Collections.unmodifiableList(lines);
	}
}