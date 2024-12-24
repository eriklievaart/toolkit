package com.eriklievaart.toolkit.io.api.sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;

public class Sha1Digest {

	private MessageDigest digest = getDigest();

	public void update(byte b) {
		digest.update(b);
	}

	public void update(byte[] input, int offset, int len) {
		digest.update(input, offset, len);
	}

	public String calculateHash() {
		return convertHashToHex(digest);
	}

	private static String convertHashToHex(final MessageDigest messageDigest) {
		try (Formatter formatter = new Formatter()) {
			for (final byte b : messageDigest.digest()) {
				// Convert the byte to hex format
				formatter.format("%02x", b);
			}
			return formatter.toString();
		}
	}

	private static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeIOException("Unable to create hash, no such algorithm", e);
		}
	}
}
