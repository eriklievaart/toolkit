package com.eriklievaart.toolkit.swing.api.laf;

import java.awt.Font;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class LookAndFeelU {

	@Test
	public void createSettingsMapVariable() {
		List<String> lines = ListTool.of("Button.font=$font");
		Map<String, Object> variables = MapTool.of("font", new Font("Serif", 0, 26));

		Map<String, Object> map = LookAndFeel.instance().createSettingsMap(lines, variables);
		Font font = (Font) map.get("Button.font");
		Check.isEqual(font.getName(), "Serif");
		Check.isEqual(font.getSize(), 26);
	}

	@Test
	public void createSettingsMapInline() {
		List<String> lines = ListTool.of("Button.font=font Serif 26");
		Map<String, Object> map = LookAndFeel.instance().createSettingsMap(lines, new Hashtable<>());
		Font font = (Font) map.get("Button.font");
		Check.isEqual(font.getName(), "Serif");
		Check.isEqual(font.getSize(), 26);
	}

	@Test
	public void convert() {
		Font font = LookAndFeel.instance().convert("font Serif 26");
		Check.isEqual(font.getName(), "Serif");
		Check.isEqual(font.getSize(), 26);
	}

	@Test
	public void loadVariables() {
		Map<String, Object> map = LookAndFeel.instance().loadVariables(ListTool.of("$font=font Serif 26"));
		Font font = (Font) map.get("font");
		Check.isEqual(font.getName(), "Serif");
		Check.isEqual(font.getSize(), 26);
	}
}
