package com.eriklievaart.toolkit.lang.api.image;

import java.awt.image.BufferedImage;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;

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

	public static BufferedImage rescalePreserveRatio(final BufferedImage source, final int width, final int height) {
		Check.notNull(source);
		double srcRatio = 1.0 * source.getWidth() / source.getHeight();
		double desRatio = 1.0 * width / height;

		boolean widthLargerInDestination = desRatio > srcRatio;
		if (widthLargerInDestination) {
			int rescaledWidth = (int) (height * srcRatio);
			return rescale(source, new Bounds(0, 0, rescaledWidth, height));
		}
		return rescale(source, new Bounds(0, 0, width, (int) (width / srcRatio)));
	}
}
