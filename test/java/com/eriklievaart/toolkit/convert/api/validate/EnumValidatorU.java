package com.eriklievaart.toolkit.convert.api.validate;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFileType;

public class EnumValidatorU {

	@Test
	public void validatePass() {
		Check.isTrue(new EnumValidator<>(VirtualFileType.class).isValid("FILE"));
	}

	@Test
	public void validateFail() {
		Check.isFalse(new EnumValidator<>(VirtualFileType.class).isValid("DIR"));
	}
}
