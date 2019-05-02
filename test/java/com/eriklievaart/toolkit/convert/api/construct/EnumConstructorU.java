package com.eriklievaart.toolkit.convert.api.construct;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFileType;

public class EnumConstructorU {

	@Test
	public void construct() {
		Check.isEqual(new EnumConstructor<>(VirtualFileType.class).constructObject("FILE"), VirtualFileType.FILE);
	}
}
