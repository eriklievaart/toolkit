package com.eriklievaart.toolkit.io.api.sha1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class Sha1 {

	public static void verify(File file, String expectedSha1) throws Sha1Exception, IOException {
		String actualSha1 = hash(file);
		if (!actualSha1.equals(expectedSha1)) {
			throw new Sha1Exception(Str.sub("sha1 % != expected hash % for file: $", actualSha1, expectedSha1, file));
		}
	}

	public static String hash(final File file) throws IOException {
		if (file == null || !file.isFile()) {
			throw new IOException(Str.sub("$ is not a file", file));
		}
		try (InputStream is = new FileInputStream(file)) {
			return sha1(is);
		}
	}

	private static String sha1(InputStream is) throws IOException {
		final BufferedInputStream bis = new BufferedInputStream(is);
		final MessageDigest messageDigest = getDigest();
		final byte[] buffer = new byte[1024];

		int read = bis.read(buffer);
		while (read != -1) {
			messageDigest.update(buffer, 0, read);
			read = bis.read(buffer);
		}
		// Convert the byte to hex format
		try (Formatter formatter = new Formatter()) {
			for (final byte b : messageDigest.digest()) {
				formatter.format("%02x", b);
			}
			return formatter.toString();
		}
	}

	private static MessageDigest getDigest() throws IOException {
		try {
			return MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new IOException("Unable to create hash, no such algorithm", e);
		}
	}
}
