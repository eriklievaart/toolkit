package com.eriklievaart.toolkit.lang.api.image;

import com.eriklievaart.toolkit.lang.api.ToString;

public class Bounds {

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	public Bounds(int x, int y, int width, int height) {
		this.y = y;
		this.x = x;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return ToString.simple(this, "$[$,$,$,$]", x, y, width, height);
	}
}