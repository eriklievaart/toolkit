package com.eriklievaart.toolkit.swing.api.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.reflect.api.annotations.AnnotationTool;

public class ReflectionMenuBuilder {

	public JMenuBar createMenuBar(Object... items) {
		JMenuBar bar = new JMenuBar();
		for (Object menu : items) {
			bar.add(createJMenu(menu));
		}
		return bar;
	}

	public JMenu createJMenu(Object items) {
		Check.notNull(items);
		Menu annotation = AnnotationTool.getLiteralAnnotation(items.getClass(), Menu.class);
		Check.notNull(annotation, "Class % not annotated with @Menu", items.getClass());
		Check.notNull(annotation.text());

		JMenu menu = new JMenu(annotation.text());
		for (NamedAction action : ReflectionActionBuilder.createActions(items)) {
			JMenuItem item = new JMenuItem(action);
			if (action.getAccelerator() != null) {
				KeyStroke key = KeyStroke.getKeyStroke(action.getAccelerator());
				Check.notNull(key, "Invalid keystroke: %", action.getAccelerator());
				item.setAccelerator(key);
			}
			menu.add(item);
		}
		return menu;
	}
}
