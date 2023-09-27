package com.eriklievaart.toolkit.lang.api.image;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class BufferedImageToolU {

	@Test
	public void calculateRescaleNone() {
		Bounds source = new Bounds(0, 0, 640, 480);
		Bounds target = new Bounds(0, 0, 640, 480);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isEqual(rescale.getWidth(), 640);
		Check.isEqual(rescale.getHeight(), 480);
	}

	@Test
	public void calculateRescaleShrinkWidth() {
		Bounds source = new Bounds(0, 0, 1000, 500);
		Bounds target = new Bounds(0, 0, 800, 800);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isEqual(rescale.getWidth(), 800);
		Check.isEqual(rescale.getHeight(), 400);
	}

	@Test
	public void calculateRescaleShrinkHeight() {
		Bounds source = new Bounds(0, 0, 500, 1000);
		Bounds target = new Bounds(0, 0, 800, 800);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isEqual(rescale.getWidth(), 400);
		Check.isEqual(rescale.getHeight(), 800);
	}

	@Test
	public void calculateRescaleStretchWidth() {
		Bounds source = new Bounds(0, 0, 800, 400);
		Bounds target = new Bounds(0, 0, 1000, 1000);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isEqual(rescale.getWidth(), 1000);
		Check.isEqual(rescale.getHeight(), 500);
	}

	@Test
	public void calculateRescaleStretchHeight() {
		Bounds source = new Bounds(0, 0, 400, 800);
		Bounds target = new Bounds(0, 0, 1000, 1000);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isEqual(rescale.getWidth(), 500);
		Check.isEqual(rescale.getHeight(), 1000);
	}

	@Test
	public void calculateRescaleTargetIsTaller() {
		Bounds source = new Bounds(0, 0, 1000, 1200);
		Bounds target = new Bounds(0, 0, 1000, 1400);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isTrue(rescale.getWidth() <= target.getWidth(), "$ > $", rescale.getWidth(), target.getWidth());
		Check.isTrue(rescale.getHeight() <= target.getHeight(), "$ > $", rescale.getHeight(), target.getHeight());

		Check.isEqual(rescale.getWidth(), 1000);
		Check.isEqual(rescale.getHeight(), 1200);
	}

	@Test
	public void calculateRescaleTargetIsWider() {
		Bounds source = new Bounds(0, 0, 1000, 1000);
		Bounds target = new Bounds(0, 0, 1200, 1000);

		Bounds rescale = BufferedImageTool.calculateRescale(source, target);
		Check.isTrue(rescale.getWidth() <= target.getWidth(), "$ > $", rescale.getWidth(), target.getWidth());
		Check.isTrue(rescale.getHeight() <= target.getHeight(), "$ > $", rescale.getHeight(), target.getHeight());

		Check.isEqual(rescale.getWidth(), 1000);
		Check.isEqual(rescale.getHeight(), 1000);
	}
}
