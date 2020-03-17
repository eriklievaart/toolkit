package com.eriklievaart.toolkit.vfs.api.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFileUrl;

public class VirtualFileUrlU {

	@Test
	public void getDuplicates() {
		List<VirtualFileUrl> list = new ArrayList<>();
		list.add(new VirtualFileUrl("file://duplicate"));
		list.add(new VirtualFileUrl("file://unique"));
		list.add(new VirtualFileUrl("file://duplicate"));

		Set<String> result = VirtualFileUrl.getDuplicates(list);
		Assertions.assertThat(result).containsExactly("file://duplicate");
	}

	@Test
	public void isParentOfPass() {
		VirtualFileUrl tmp = new VirtualFileUrl("file://tmp");
		VirtualFileUrl file = new VirtualFileUrl("file://tmp/file");
		Check.isTrue(tmp.isParentOf(file));
	}

	@Test
	public void isParentOfFail() {
		VirtualFileUrl tmp = new VirtualFileUrl("file://tmp");
		VirtualFileUrl file = new VirtualFileUrl("file://tmp/file");
		Check.isFalse(file.isParentOf(tmp));
	}

	@Test
	public void isParentOfItself() {
		VirtualFileUrl file = new VirtualFileUrl("file://tmp/file");
		Check.isFalse(file.isParentOf(file));
	}

	@Test
	public void isParentOfSimilarButFail() {
		VirtualFileUrl tmp = new VirtualFileUrl("file://tmp/fil");
		VirtualFileUrl file = new VirtualFileUrl("file://tmp/file");
		Check.isFalse(tmp.isParentOf(file));
	}
}