package com.eriklievaart.toolkit.vfs.api.function;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class VirtualFileLineIO implements LineIO {
	private LogTemplate log = new LogTemplate(getClass());

	private VirtualFile file;

	public VirtualFileLineIO(VirtualFile file) {
		this.file = file;
	}

	@Override
	public void store(List<String> lines) {
		file.getContent().writeLines(lines);
	}

	@Override
	public List<String> load() {
		if (file.exists()) {
			return file.getContent().readLines();
		} else {
			log.warn("$ does not exist, returning empty list", file);
			return Collections.emptyList();
		}
	}
}
