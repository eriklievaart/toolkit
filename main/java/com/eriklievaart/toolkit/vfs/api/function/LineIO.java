package com.eriklievaart.toolkit.vfs.api.function;

import java.util.List;

public interface LineIO {

	public void store(List<String> lines);

	public List<String> load();
}
