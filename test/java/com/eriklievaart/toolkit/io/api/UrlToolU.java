package com.eriklievaart.toolkit.io.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class UrlToolU {

	@Test
	public void getFullFileName() {
		Check.isEqual(UrlTool.getName("file:///tmp/test.123"), "test.123");
	}

	@Test
	public void getFullFileNamePrefixDot() {
		Check.isEqual(UrlTool.getName("file:///tmp/.hal-mtab"), ".hal-mtab");
	}

	@Test
	public void getFullFileNameRoot() {
		Check.isEqual(UrlTool.getBaseName("file:///"), "/");
	}

	@Test
	public void getNameWindows() {
		Check.isEqual(UrlTool.getName("file://c:\\tmp\\test.123"), "test.123");
	}

	@Test
	public void getExtension() {
		Check.isEqual(UrlTool.getExtension("file:///tmp/test.123"), "123");
	}

	@Test
	public void getExtensionPrefixDot() {
		Check.isEqual(UrlTool.getExtension("file:///tmp/.hal-mtab"), "");
	}

	@Test
	public void getExtensionTrailingDot() {
		Check.isEqual(UrlTool.getExtension("file:///tmp/test."), "");
	}

	@Test
	public void getExtensionNone() {
		Check.isEqual(UrlTool.getExtension("file:///tmp/test"), "");
	}

	@Test
	public void getBaseName() {
		Check.isEqual(UrlTool.getBaseName("file:///tmp/test.123"), "test");
	}

	@Test
	public void getBaseNameRoot() {
		Check.isEqual(UrlTool.getBaseName("file:///"), "/");
	}

	@Test
	public void getBaseNameTrailingSlash() {
		Check.isEqual(UrlTool.getBaseName("file:///tmp/test/"), "test/");
	}

	@Test
	public void getBaseNamePrefixDot() {
		Check.isEqual(UrlTool.getBaseName("file:///tmp/.hal-mtab"), ".hal-mtab");
	}

	@Test
	public void getBaseNameTrailingDot() {
		Check.isEqual(UrlTool.getBaseName("file:///tmp/test."), "test.");
	}

	@Test
	public void getBaseNameWindows() {
		Check.isEqual(UrlTool.getBaseName("c:/tmp/test.csv"), "test");
	}

	@Test
	public void getBaseNamePath() {
		Check.isEqual(UrlTool.getBaseName("tmp/test.csv"), "test");
	}

	@Test
	public void getParentURL() {
		Check.isEqual(UrlTool.getParent("file:///tmp"), "file:///");
	}

	@Test
	public void getParentWindows() {
		Check.isEqual(UrlTool.getParent("file://c:\\tmp"), "file://c:\\");
	}

	@Test
	public void getParentEmpty() {
		Check.isEqual(UrlTool.getParent("file:///me//"), null);
	}

	@Test
	public void getParentURLTrailingSlash() {
		Check.isEqual(UrlTool.getParent("file:///tmp/"), "file:///");
	}

	@Test
	public void getProtocol() {
		CheckStr.isEqual(UrlTool.getProtocol("http://www.example.com").get(), "http");
	}

	@Test
	public void getProtocolMissing() {
		Check.isFalse(UrlTool.getProtocol("www.example.com").isPresent());
	}

	@Test
	public void getProtocolInvalid() {
		Check.isFalse(UrlTool.getProtocol("ht/tp://www.example.com").isPresent());
	}

	@Test
	public void addSlashRoot() {
		Check.isEqual(UrlTool.addSlash("file:///"), "file:///");
	}

	@Test
	public void addSlashDir() {
		Check.isEqual(UrlTool.addSlash("file:///tmp"), "file:///tmp/");
	}

	@Test
	public void addSlashIgnore() {
		Check.isEqual(UrlTool.addSlash("file:///tmp/"), "file:///tmp/");
	}

	@Test
	public void addSlashIgnoreWindows() {
		Check.isEqual(UrlTool.addSlash("file:///c:\\tmp\\"), "file:///c:\\tmp\\");
	}

	@Test
	public void removeLeadingSlashesNull() {
		Check.isEqual(UrlTool.removeLeadingSlashes(null), null);
	}

	@Test
	public void removeLeadingSlashesRoot() {
		Check.isEqual(UrlTool.removeLeadingSlashes("/"), "");
	}

	@Test
	public void removeLeadingSlashes() {
		Check.isEqual(UrlTool.removeLeadingSlashes("///tmp"), "tmp");
	}

	@Test
	public void removeLeadingSlashesNone() {
		Check.isEqual(UrlTool.removeLeadingSlashes("foo/bar"), "foo/bar");
	}

	@Test
	public void removeLeadingSlashesWindows() {
		Check.isEqual(UrlTool.removeLeadingSlashes("\\tmp"), "tmp");
	}

	@Test
	public void removeTrailingSlashRoot() {
		Check.isEqual(UrlTool.removeTrailingSlash("/"), "/");
	}

	@Test
	public void removeTrailingSlashNull() {
		Check.isNull(UrlTool.removeTrailingSlash(null));
	}

	@Test
	public void removeTrailingSlashRootPrefix() {
		Check.isEqual(UrlTool.removeTrailingSlash("file:///"), "file:///");
	}

	@Test
	public void removeTrailingSlashRootWindows() {
		Check.isEqual(UrlTool.removeTrailingSlash("file://c:\\"), "file://c:/");
	}

	@Test
	public void removeTrailingSlashIgnore() {
		Check.isEqual(UrlTool.removeTrailingSlash("file:///tmp"), "file:///tmp");
	}

	@Test
	public void removeTrailingSlashDir() {
		Check.isEqual(UrlTool.removeTrailingSlash("file:///tmp/"), "file:///tmp");
	}

	@Test
	public void removeTrailingSlashWindowsDir() {
		Check.isEqual(UrlTool.removeTrailingSlash("c:\\temp\\"), "c:/temp");
	}

	@Test
	public void escape() {
		Check.isEqual(UrlTool.escape("test with spaces"), "test%20with%20spaces");
	}

	@Test
	public void unescape() {
		Check.isEqual(UrlTool.unescape("test%20with%20spaces"), "test with spaces");
	}

	@Test
	public void unescapePercentile() {
		Check.isEqual(UrlTool.unescape("file:///%25.txt"), "file:///%.txt");
	}

	@Test(expected = AssertionException.class)
	public void unescapeInvalid() {
		UrlTool.unescape("test%20with%20invalid%AAescape");
	}

	@Test(expected = AssertionException.class)
	public void unescapeTrailing() {
		UrlTool.unescape("trailing%2");
	}

	@Test
	public void appendRoot() {
		Check.isEqual(UrlTool.append("/", "etc"), "/etc");
	}

	@Test
	public void appendEmpty() {
		Check.isEqual(UrlTool.append("/", ""), "/");
	}

	@Test
	public void appendNoTrailingSlash() {
		Check.isEqual(UrlTool.append("file:///etc", "fstab"), "file:///etc/fstab");
	}

	@Test
	public void appendWithTrailingSlash() {
		Check.isEqual(UrlTool.append("file:///etc/", "fstab"), "file:///etc/fstab");
	}

	@Test
	public void appendSlashPrefixNoTrailing() {
		Check.isEqual(UrlTool.append("file:///etc", "/fstab"), "file:///etc/fstab");
	}

	@Test
	public void appendSlashPrefixWithTrailing() {
		Check.isEqual(UrlTool.append("file:///etc/", "/fstab"), "file:///etc/fstab");
	}

	@Test
	public void appendWindows() {
		Check.isEqual(UrlTool.append("file://c:\\temp\\", "\\junk"), "file://c:\\temp\\junk");
	}

	@Test
	public void appendMultiple() {
		Check.isEqual(UrlTool.append("/media/", "/cdrom", "dir"), "/media/cdrom/dir");
	}

	@Test
	public void getHead() {
		Check.isEqual(UrlTool.getHead("path/to/something"), "path");
	}

	@Test
	public void getHeadNull() {
		Check.isNull(UrlTool.getHead(null), null);
	}

	@Test
	public void getHeadSlash() {
		Check.isEqual(UrlTool.getHead("/path"), "path");
	}

	@Test
	public void getHeadUrl() {
		Check.isEqual(UrlTool.getHead("https://path/to/something"), "path");
	}

	@Test
	public void getHeadWindowsRoot() {
		Check.isEqual(UrlTool.getHead("file://c:\\tmp\\file"), "c:");
	}

	@Test
	public void getHeadWindows() {
		Check.isEqual(UrlTool.getHead("\\tmp\\file"), "tmp");
	}

	@Test
	public void getTail() {
		Check.isEqual(UrlTool.getTail("path/to/something"), "to/something");
	}

	@Test
	public void getTailBackslash() {
		Check.isEqual(UrlTool.getTail("pat\\to/something"), "to/something");
	}

	@Test
	public void getTailNone() {
		Check.isNull(UrlTool.getTail("notail"));
	}

	@Test
	public void getTailUrl() {
		Check.isEqual(UrlTool.getTail("https://path/to/something"), "to/something");
	}

	@Test
	public void getDomain() {
		Check.isEqual(UrlTool.getDomain("https://example.com/ignore"), "example.com");
	}

	@Test
	public void getDomainSlash() {
		Check.isEqual(UrlTool.getDomain("https://example.com/"), "example.com");
	}

	@Test
	public void getDomainNoProtocol() {
		Check.isEqual(UrlTool.getDomain("example.com/ignore"), "example.com");
	}

	@Test
	public void getDomainMissing() {
		Check.isNull(UrlTool.getDomain("/missing"));
	}

	@Test
	public void getDomainMissingShort() {
		Check.isNull(UrlTool.getDomain("/"));
	}

	@Test
	public void getRelativePath() {
		Check.isEqual(UrlTool.getRelativePath("/path", "/path/to/something"), "to/something");
	}

	@Test
	public void getRelativePathShouldWork() {
		Check.isEqual(UrlTool.getRelativePath("/data/cheat/files", "/data/cheat/files/test.txt"), "test.txt");
	}

	@Test
	public void getRelativePathTrailingSlash() {
		Check.isEqual(UrlTool.getRelativePath("/path/", "/path/to/something"), "to/something");
	}

	@Test
	public void getRelativePathMismatch() {
		Check.isNull(UrlTool.getRelativePath("/somewhere", "/elsewhere"));
	}

	@Test
	public void getRelativePathWrongOrder() {
		Check.isNull(UrlTool.getRelativePath("/data/cheat/files/test.txt", "/data/cheat/files"));
	}

	@Test
	public void getRelativePathWindows() {
		Check.isEqual(UrlTool.getRelativePath("c:/data/cheat/files", "c:\\data\\cheat\\files\\test.txt"), "test.txt");
	}

	@Test
	public void getPathSingle() {
		Check.isEqual(UrlTool.getPath("file:/"), "/");
	}

	@Test
	public void getPathDouble() {
		Check.isEqual(UrlTool.getPath("file://"), "");
	}

	@Test
	public void getPathTriple() {
		Check.isEqual(UrlTool.getPath("file:///"), "/");
	}

	@Test
	public void getPath() {
		Check.isEqual(UrlTool.getPath("file:///media/2000GB/test.xml"), "/media/2000GB/test.xml");
	}

	@Test
	public void getPathWindows() {
		Check.isEqual(UrlTool.getPath("file://c:/tmp"), "c:/tmp");
	}

	@Test
	public void getPathRelative() {
		Check.isEqual(UrlTool.getPath("path/to/something"), "path/to/something");
	}
}
