package com.eriklievaart.toolkit.reflect.api;

import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ListModel;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.reflect.api.InstanceTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.api.method.MethodWrapper;

public class InstanceToolU {

	@Test
	public void populateProperty() {
		JList list = new JList();
		ListModel model = new DefaultListModel();
		Check.isFalse(list.getModel() == model);

		InstanceTool.populate(list, MapTool.of("model", model));
		Check.isTrue(list.getModel() == model);
	}

	@Test
	public void populateField() {
		Point point = new Point();
		int x = 5;
		InstanceTool.populate(point, MapTool.of("x", Integer.valueOf(x)));
		Check.isEqual(point.x, x);
	}

	@Test(expected = ReflectException.class)
	public void populateMissing() {
		InstanceTool.populate(new Point(), MapTool.of("brocolli", 2));
	}

	@Test
	public void methodWrapper() {
		Thread thread = new Thread();
		thread.setPriority(4);
		MethodWrapper method = InstanceTool.getMethodWrapper("getPriority", thread);
		Check.isEqual(method.invoke(new Object[] {}), new Integer(4));

		try {
			Check.isEqual(method.invoke(new Object[] { 45 }), new Integer(4));
			throw new AssertionException("Should be impossible");
		} catch (ReflectException e) {
		}
	}

	@Test
	public void methodWithArgumentsMatch() {
		JMenu menu = new JMenu();
		JMenuItem item = new JMenuItem();

		MethodWrapper wrapper = InstanceTool.getMethodWrapper("add", menu, "java.awt.Component");
		wrapper.invoke(item);
	}

	@Test(expected = ReflectException.class)
	public void methodWithArgumentsMismatch() {
		JMenu menu = new JMenu();
		JMenuItem item = new JMenuItem();

		MethodWrapper wrapper = InstanceTool.getMethodWrapper("add", menu, "java.awt.Action");
		wrapper.invoke(item);
	}
}
