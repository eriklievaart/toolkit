package com.eriklievaart.toolkit.vfs.api.file;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class VirtualFileUrl {

	private final String url;

	public VirtualFileUrl(String url) {
		CheckStr.notBlank(url);
		CheckStr.notContainsIgnoreCase(url, "/../");
		CheckStr.notBlank(UrlTool.getPath(url));
		UrlTool.getProtocol(url).orElseThrow(() -> new RuntimeIOException("Cannot determine protocol of %", url));
		this.url = url.trim().replaceAll("\\\\", "/");
	}

	public static Set<String> getDuplicates(List<VirtualFileUrl> list) {
		Check.notNull(list);
		Set<String> unique = new HashSet<>();
		Set<String> duplicates = new HashSet<>();

		for (VirtualFileUrl entry : list) {
			if (unique.contains(entry.url)) {
				duplicates.add(entry.url);
			}
			unique.add(entry.url);
		}
		return duplicates;
	}

	public String getProtocol() {
		return UrlTool.getProtocol(url).get();
	}

	public String getPathEscaped() {
		return UrlTool.escape(UrlTool.getPath(url));
	}

	public String getNameEscaped() {
		return UrlTool.escape(UrlTool.getName(url));
	}

	public String getBaseName() {
		return UrlTool.getBaseName(url);
	}

	public String getBaseNameEscaped() {
		return UrlTool.escape(getBaseName());
	}

	public String getExtension() {
		return UrlTool.getExtension(url);
	}

	public String getUrlEscaped() {
		return getProtocol() + "://" + getPathEscaped();
	}

	public String getUrlUnescaped() {
		return url;
	}

	public boolean isParentOf(VirtualFileUrl test) {
		return test.url.startsWith(url + "/");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VirtualFileUrl) {
			VirtualFileUrl other = (VirtualFileUrl) obj;
			return url.equals(other.url);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$]", url);
	}
}