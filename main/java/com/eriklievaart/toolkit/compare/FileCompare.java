package com.eriklievaart.toolkit.compare;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileCompare {

	public static boolean contentEquals(File file1, File file2) throws IOException {
		boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists()) {
			return false;
		}

		if (!file1Exists) {
			// two not existing files are equal
			return true;
		}

		if (file1.isDirectory() || file2.isDirectory()) {
			// don't want to compare directory contents
			throw new IOException("Can't compare directories, only files");
		}

		if (file1.length() != file2.length()) {
			// lengths differ, cannot be equal
			return false;
		}

		if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
			// same file
			return true;
		}

		try (InputStream input1 = new FileInputStream(file1)) {
			try (InputStream input2 = new FileInputStream(file2);) {
				return contentEquals(input1, input2);
			}
		}
	}

	public static boolean contentEquals(InputStream original1, InputStream original2) throws IOException {
		InputStream input1 = buffer(original1);
		InputStream input2 = buffer(original2);

		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}
		int ch2 = input2.read();
		return ch2 == -1;
	}

	private static InputStream buffer(InputStream original1) {
		return original1 instanceof BufferedInputStream ? original1 : new BufferedInputStream(original1);
	}

}
