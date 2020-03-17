package com.eriklievaart.toolkit.lang.api;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class Obj {

	private Obj() {
	}

	public static boolean equals(final Object first, final Object second) {
		return first != null && first.equals(second);
	}

	public static String toString(final Object obj) {
		return obj == null ? Str.NULL : obj.toString();
	}

	public static void synchronizedWait(final Object obj) throws InterruptedException {
		synchronized (obj) {
			obj.wait();
		}
	}

	public static void synchronizedWait(final Object obj, final long timeout) throws InterruptedException {
		synchronized (obj) {
			obj.wait(timeout);
		}
	}

	public static void synchronizedNotifyAll(final Object obj) {
		synchronized (obj) {
			obj.notifyAll();
		}
	}
}