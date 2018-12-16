package com.eriklievaart.toolkit.lang.api.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BoundedGraphics {

	private final Bounds bounds;
	private final Graphics g;

	public BoundedGraphics(Bounds bounds, Graphics g) {
		this.bounds = bounds;
		this.g = g;
	}

	public BoundedGraphics subBounds(Bounds sub) {
		assertInBounds(sub.getX(), sub.getY(), sub.getWidth(), sub.getHeight());
		Bounds b = new Bounds(sub.getX() + bounds.getX(), sub.getY() + bounds.getY(), sub.getWidth(), sub.getHeight());
		return new BoundedGraphics(b, g);
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void fillOval(Color color, Bounds oval) {
		g.setColor(color);
		assertInBounds(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight());
		g.fillOval(oval.getX() + bounds.getX(), oval.getY() + bounds.getY(), oval.getWidth(), oval.getHeight());
	}

	public void drawRect(Color color, Bounds rect) {
		g.setColor(color);
		assertInBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		g.drawRect(rect.getX() + bounds.getX(), rect.getY() + bounds.getY(), rect.getWidth() - 1, rect.getHeight() - 1);
	}

	public void fillRect(Color color, Bounds rect) {
		g.setColor(color);
		assertInBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		g.fillRect(rect.getX() + bounds.getX(), rect.getY() + bounds.getY(), rect.getWidth(), rect.getHeight());
	}

	public void drawLine(Color color, Dot d1, Dot d2) {
		assertInBounds(d1.getX(), d1.getY(), 0, 0);
		assertInBounds(d2.getX(), d2.getY(), 0, 0);

		g.setColor(color);
		int bx = bounds.getX();
		int by = bounds.getY();
		g.drawLine(d1.getX() + bx, d1.getY() + by, d2.getX() + bx, d2.getY() + by);
	}

	public void drawVerticalLine(Color color, Dot top, int height) {
		drawLine(color, top, new Dot(top.getX(), top.getY() + height));
	}

	public void drawPixel(Color color, Dot dot) {
		fillRect(color, new Bounds(dot.getX(), dot.getY(), 1, 1));
	}

	public void drawImage(BufferedImage img) {
		drawImage(img, bounds.getX(), bounds.getY());
	}

	public void drawImage(BufferedImage img, int x, int y) {
		assertInBounds(x, y, img.getWidth(), img.getHeight());
		g.drawImage(img, x + bounds.getX(), y + bounds.getY(), img.getWidth(), img.getHeight(), null);
	}

	public void drawImage(BufferedImage img, Bounds b) {
		assertInBounds(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		g.drawImage(img, b.getX() + bounds.getX(), b.getY() + bounds.getY(), b.getWidth(), b.getHeight(), null);
	}

	public void drawImageCentered(BufferedImage img) {
		int xOffset = (bounds.getWidth() - img.getWidth()) / 2;
		int yOffset = (bounds.getHeight() - img.getHeight()) / 2;
		drawImage(img, xOffset, yOffset);
	}

	public void drawString(Color c, Font font, String value, Dot topLeft) {
		g.setColor(c);
		g.setFont(font);

		int width = g.getFontMetrics().stringWidth(value);
		int height = g.getFontMetrics().getHeight();
		assertInBounds(topLeft.getX(), topLeft.getY() - font.getSize(), width, height);

		g.drawString(value, topLeft.getX() + bounds.getX(), topLeft.getY() + bounds.getY());
	}

	private void assertInBounds(int x, int y, int width, int height) {
		assert x >= 0;
		assert y >= 0;
		assert x + width <= bounds.getWidth() : x + width + " > " + bounds.getWidth();
		assert y + height <= bounds.getHeight() : y + height + " > " + bounds.getHeight();
	}
}
