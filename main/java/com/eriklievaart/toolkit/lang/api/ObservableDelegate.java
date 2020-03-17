package com.eriklievaart.toolkit.lang.api;

import java.util.Observable;

public class ObservableDelegate extends Observable {
	@Override
	public void setChanged() {
		super.setChanged();
	}

	public void changeAndnotifyObservers() {
		setChanged();
		super.notifyObservers();
	}

	public void changeAndnotifyObservers(final Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}