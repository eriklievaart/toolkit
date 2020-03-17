package com.eriklievaart.toolkit.io.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class ZipStreamTool {

	public static List<String> entries(InputStream is) throws IOException {
		List<String> files = NewCollection.list();
		try (ZipInputStream zis = new ZipInputStream(is)) {
			for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis.getNextEntry()) {
				files.add(entry.getName());
			}
		}
		return ListTool.sortedCopy(files);
	}

	public static void zip(Map<String, Supplier<InputStream>> pathWithData, OutputStream os) throws IOException {
		try (ZipOutputStream zos = new ZipOutputStream(os)) {
			for (String path : pathWithData.keySet()) {
				zos.putNextEntry(new ZipEntry(path));
				copyFileContents(pathWithData.get(path).get(), zos);
				zos.closeEntry();
			}
		}
	}

	public static void unzip(InputStream is, Function<String, OutputStream> function) throws IOException {
		try (ZipInputStream zis = new ZipInputStream(is)) {
			for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis.getNextEntry()) {
				String name = entry.getName();
				if (!entry.isDirectory()) {
					try (OutputStream os = function.apply(name)) {
						StreamTool.copyStreamNoClose(zis, os);
					}
				}
			}
		}
	}

	public static String unzipEntry(InputStream is, String path) throws IOException {
		try (ZipInputStream zis = new ZipInputStream(is)) {
			for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis.getNextEntry()) {
				String name = entry.getName();
				if (name.equals(path)) {
					return StreamTool.toString(zis);
				}
			}
		}
		throw new IOException(Str.sub("no entry % in zip", path));
	}

	private static void copyFileContents(InputStream is, ZipOutputStream zos) throws IOException {
		try {
			int length;
			byte[] buffer = new byte[1024];
			while ((length = is.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			is.close();
		}
	}
}