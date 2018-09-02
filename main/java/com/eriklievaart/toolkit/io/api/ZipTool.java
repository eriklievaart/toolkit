package com.eriklievaart.toolkit.io.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class ZipTool {
	private static LogTemplate log = new LogTemplate(StreamTool.class);

	public static List<File> unzip(InputStream is, File directory) {
		List<File> files = new ArrayList<>();
		directory.mkdirs();

		try (ZipInputStream zis = new ZipInputStream(is)) {
			for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
				File file = new File(directory, ze.getName());
				unzipEntry(zis, file);
				files.add(file);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
		log.debug("Done");
		return files;
	}

	private static void unzipEntry(ZipInputStream zis, File newFile) {
		log.debug("file unzip : " + newFile.getAbsoluteFile());
		newFile.getParentFile().mkdirs();

		byte[] buffer = new byte[1024];
		try (FileOutputStream fos = new FileOutputStream(newFile)) {
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			zis.closeEntry();

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}
}
