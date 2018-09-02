package com.eriklievaart.toolkit.test.api;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.FormattedException;

public class BombSquadU {

	@Test(expected = AssertionException.class)
	public void explodeNullPointerFail() {
		BombSquad.diffuse(NullPointerException.class, "message", () -> {
			AtomicReference<String> reference = new AtomicReference<>();
			reference.get().getBytes(); // NPE
		});
	}

	@Test
	public void explodeNullPointerPass() {
		BombSquad.diffuse(NullPointerException.class, (String) null, () -> {
			AtomicReference<String> reference = new AtomicReference<>();
			reference.get().getBytes(); // NPE
		});
	}

	@Test(expected = FormattedException.class)
	public void explodeMessageFail() {
		BombSquad.diffuse(IOException.class, "bla", () -> {
			throw new IOException("pineapple");
		});
	}

	@Test
	public void explodeMessagePass() {
		BombSquad.diffuse(IOException.class, "bla", () -> {
			throw new IOException("balblabal");
		});
	}
}
