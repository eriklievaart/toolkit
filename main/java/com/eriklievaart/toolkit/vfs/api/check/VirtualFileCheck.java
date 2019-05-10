package com.eriklievaart.toolkit.vfs.api.check;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class VirtualFileCheck {

	public static void isFile(VirtualFile file) {
		Check.notNull(file);
		Check.isTrue(file.isFile(), "not a file: $", file);
	}

	public static void isDirectory(VirtualFile file) {
		Check.notNull(file);
		Check.isTrue(file.isDirectory(), "not a directory: $", file);
	}

	public static void exists(VirtualFile file) {
		Check.notNull(file);
		Check.isTrue(file.exists(), "does not exist: $", file);
	}

	public static void isEmpty(VirtualFile file) {
		if (file.isFile()) {
			Check.isTrue(!file.exists() || Str.isBlank(file.getContent().readString()), "file not empty: $", file);
		} else {
			Check.isTrue(!file.exists() || file.getChildren().isEmpty(), "has children: $", file);
		}
	}
}
