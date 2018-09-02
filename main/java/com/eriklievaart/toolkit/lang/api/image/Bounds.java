package com.eriklievaart.toolkit.lang.api.image;

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
}
