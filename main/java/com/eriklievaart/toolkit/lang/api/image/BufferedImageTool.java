package com.eriklievaart.toolkit.lang.api.image;

import java.awt.image.BufferedImage;
import java.util.List;

public class BufferedImageTool {

	private BufferedImageTool() {
	}

	public static BufferedImage paintLayers(int width, int height, List<BufferedImage> layers) {
		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BoundedGraphics g = new BoundedGraphics(new Bounds(0, 0, width, height), combined.getGraphics());

		for (BufferedImage layer : layers) {
			g.drawImageCentered(layer);
		}
		return combined;
	}

	public static BufferedImage rescale(BufferedImage source, Bounds b) {
		BufferedImage image = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(source, b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);
		return image;
	}

}
