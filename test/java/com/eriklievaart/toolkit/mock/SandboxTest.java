package com.eriklievaart.toolkit.mock;

import java.io.File;

import org.junit.After;
import org.junit.Before;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFileSystem;
import com.eriklievaart.toolkit.vfs.api.file.SystemFile;

/**
 * Relative paths should be entered WITHOUT any escape characters.
 *
 * @author Erik Lievaart
 */
public class SandboxTest {
	private static final String TMP_DIR = System.getProperty("java.io.tmpdir");

	protected final File root = new File(TMP_DIR, "sandbox/" + System.currentTimeMillis());
	protected final MemoryFileSystem memoryFileSystem = new MemoryFileSystem();
	protected final MemoryFileSystemCheck memoryCheck = new MemoryFileSystemCheck(memoryFileSystem);

	@Before
	public final void createSandbox() {
		root.mkdirs();
	}

	@After
	public final void deleteSandboxFiles() {
		FileTool.delete(root);
	}

	public File file(final String path) {
		Check.isFalse(UrlTool.getProtocol(path).isPresent(), "expecting relative path: %", path);
		return new File(root, path);
	}

	public SystemFile systemFile(final String path) {
		return new SystemFile(file(path));
	}

	public MemoryFile memoryFile(final String path) {
		Check.isFalse(UrlTool.getProtocol(path).isPresent(), "expecting relative path: %", path);
		return memoryFileSystem.resolve(path);
	}

	public SystemFile createDirectory(final String path) {
		SystemFile sf = systemFile(path);
		sf.mkdir();
		return sf;
	}

	public SystemFile createFile(final String path) {
		SystemFile sf = systemFile(path);
		sf.createFile();
		return sf;
	}

	public String urlEscaped(final String path) {
		return systemFile(path).getUrl().getUrlEscaped();
	}

	public String url(final String path) {
		return systemFile(path).getUrl().getUrlUnescaped();
	}

	public String quoted(final String path) {
		return Str.quote(urlEscaped(path));
	}

	public void checkIsFile(final String path) {
		CheckFile.isFile(file(path));
	}

	public void checkIsFile(final String path, String contents) {
		CheckFile.isFile(file(path), contents);
	}

	public void checkIsDirectory(final String path) {
		CheckFile.isDirectory(file(path));
	}

	public void checkIsEmptyDirectory(final String path) {
		CheckFile.isEmptyDirectory(file(path));
	}

	public void checkIsDirectoryWithChildren(final String path) {
		CheckFile.isDirectoryWithChildren(file(path));
	}

	public void checkExists(final String path) {
		CheckFile.exists(file(path));
	}

	public void checkNotExists(final String path) {
		CheckFile.notExists(file(path));
	}

	public SandboxTest newInstance() {
		return new SandboxTest();
	}
}