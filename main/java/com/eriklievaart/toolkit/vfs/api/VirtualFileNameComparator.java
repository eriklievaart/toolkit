package com.eriklievaart.toolkit.vfs.api;

import java.util.Comparator;

class VirtualFileNameComparator implements Comparator<String> {

	@Override
	public int compare(final String o1, final String o2) {
		return o1 == null ? -1 : o1.compareToIgnoreCase(o2);
	}
}
