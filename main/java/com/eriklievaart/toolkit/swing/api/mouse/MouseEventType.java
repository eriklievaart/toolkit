package com.eriklievaart.toolkit.swing.api.mouse;

import java.awt.event.MouseEvent;

public enum MouseEventType {

	MOUSE_CLICKED(MouseEvent.MOUSE_CLICKED),

	MOUSE_PRESSED(MouseEvent.MOUSE_PRESSED),

	MOUSE_RELEASED(MouseEvent.MOUSE_RELEASED);

	private int type;

	private MouseEventType(int type) {
		this.type = type;
	}

	public int getId() {
		return type;
	}

}
