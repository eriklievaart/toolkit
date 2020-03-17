package com.eriklievaart.toolkit.vfs.api.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.zip.ZipInputStream;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.ZipStreamTool;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.vfs.api.VirtualFileScanner;
import com.eriklievaart.toolkit.vfs.api.check.VirtualFileCheck;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFile;

public class VirtualFileZipper {

	public static void zip(VirtualFile source, VirtualFile zip) {
		VirtualFileCheck.exists(source);

		try {
			if (source.isFile()) {
				zipFile(source, zip);
			} else {
				zipDirectory(source, zip);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void unzip(VirtualFile zip, VirtualFile destination) {
		InputStream input = zip.getContent().getInputStream();
		try {
			ZipStreamTool.unzip(input, path -> {
				return destination.resolve(path).getContent().getOutputStream();
			});
		} catch (IOException e) {
			throw new RuntimeIOException("unable to read zip file $", e, zip);
		}
	}

	public static List<String> entries(VirtualFile zip) {
		try (ZipInputStream is = new ZipInputStream(zip.getContent().getInputStream())) {
			return ZipStreamTool.entries(zip.getContent().getInputStream());
		} catch (IOException e) {
			throw new RuntimeIOException("unable to read zip file $", e, zip);
		}
	}

	private static void zipFile(VirtualFile source, VirtualFile zip) throws IOException {
		Supplier<InputStream> value = () -> source.getContent().getInputStream();
		ZipStreamTool.zip(MapTool.of(source.getName(), value), zip.getContent().getOutputStream());
	}

	private static void zipDirectory(VirtualFile source, VirtualFile zip) throws IOException {
		Map<String, Supplier<InputStream>> map = NewCollection.map();
		for (VirtualFile file : new VirtualFileScanner(source)) {
			map.put(source.getRelativePathOf(file), () -> file.getContent().getInputStream());
		}
		ZipStreamTool.zip(map, zip.getContent().getOutputStream());
	}
}