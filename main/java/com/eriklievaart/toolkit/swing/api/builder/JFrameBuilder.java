package com.eriklievaart.toolkit.swing.api.builder;

import javax.swing.JFrame;

public class JFrameBuilder {

	private JFrame frame;

	public JFrameBuilder(String name) {
		frame = new JFrame();
		frame.setName(name);
	}

	public JFrameBuilder title(String title) {
		frame.setTitle(title);
		return this;
	}

	public JFrame create() {
		return frame;
	}

	public JFrameBuilder exitOnClose() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return this;
	}
}
