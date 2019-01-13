package com.eriklievaart.toolkit.vfs.api.function;

import java.util.List;
import java.util.function.Function;

import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class FunctionIO<T> {

	private LineIO io;
	private Function<T, String> storeFunction;
	private Function<String, T> loadFunction;

	public FunctionIO(Function<T, String> store, Function<String, T> load) {
		this(store, load, new ListLineIO());
	}

	public FunctionIO(Function<T, String> store, Function<String, T> load, VirtualFile file) {
		this(store, load, new VirtualFileLineIO(file));
	}

	public FunctionIO(Function<T, String> store, Function<String, T> load, LineIO io) {
		this.storeFunction = store;
		this.loadFunction = load;
		this.io = io;
	}

	public List<T> load() {
		List<T> result = NewCollection.list();
		for (String line : io.load()) {
			try {
				result.add(loadFunction.apply(line));
			} catch (Exception e) {
				throw new FormattedException("invalid line: $", e, line);
			}
		}
		return result;
	}

	public void store(List<T> data) {
		List<String> lines = NewCollection.list();
		for (T object : data) {
			try {
				lines.add(storeFunction.apply(object));
			} catch (Exception e) {
				throw new FormattedException("invalid object: $", e, object);
			}
		}
		io.store(lines);
	}
}
