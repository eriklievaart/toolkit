package com.eriklievaart.toolkit.swing.api.builder;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridPanelBuilder {

	private JPanel panel;

	public GridPanelBuilder(int rows, int cols) {
		panel = new JPanel(new GridLayout(rows, cols));
	}

	public GridPanelBuilder(int rows, int cols, int hgap, int vgap) {
		panel = new JPanel(new GridLayout(rows, cols, hgap, vgap));
	}

	public JPanel create() {
		return panel;
	}

	public void add(Component component) {
		panel.add(component);
	}
}
