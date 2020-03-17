package com.eriklievaart.toolkit.io.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.mock.SandboxTest;
import com.eriklievaart.toolkit.vfs.api.file.MemoryFile;

public class ZipStreamToolU extends SandboxTest {

	@Test
	public void unzipEntry() throws IOException {
		Map<String, Supplier<InputStream>> map = NewCollection.map();
		map.put("a.txt", () -> StreamTool.toInputStream("aaaa"));
		map.put("b.txt", () -> StreamTool.toInputStream("bbbb"));
		map.put("c.txt", () -> StreamTool.toInputStream("cccc"));

		MemoryFile zip = memoryFile("file.zip");
		ZipStreamTool.zip(map, zip.getOutputStream());

		String result = ZipStreamTool.unzipEntry(zip.getInputStream(), "b.txt");
		Check.isEqual(result, "bbbb");
	}
}