package com.eriklievaart.toolkit.io.api.properties;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.MapAssert;
import org.junit.Test;

import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.io.api.StringBuilderOutputStream;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class PropertiesUtfU {

	@Test
	public void store() {
		Check.isEqual(storeUtf(MapTool.of("a", "1")), "a=1\n");
		Check.isEqual(storeUtf(MapTool.of("a\\a", "1=1")), "a\\ba=1\\e1\n");
	}

	@Test
	public void load() {
		assertLoad("a=1").containsEntry("a", "1");
		assertLoad("a\\ba\\ha=1\\e1").containsEntry("a\\a#a", "1=1");
	}

	@Test
	public void loadIgnoreCommentsAndBlankLines() {
		assertLoad("#a=1\n\n \t \n").isEmpty();
	}

	@Test
	public void storeLoad() {
		Map<String, String> map = MapTool.of("a", "b", "c", "d");

		StringBuilderOutputStream os = new StringBuilderOutputStream();
		PropertiesUtf.store(map, os);
		String result = os.getResult();
		CheckStr.contains(result, "a=b\n");
		CheckStr.contains(result, "c=d\n");

		Map<String, String> parsed = PropertiesUtf.load(StreamTool.toInputStream(result));
		Assertions.assertThat(parsed).isEqualTo(map);
	}

	@Test
	public void escape() {
		Check.isEqual(PropertiesUtf.escape("#ab\\cd=ef\\gh"), "\\hab\\bcd\\eef\\bgh");
		Check.isEqual(PropertiesUtf.unescape(PropertiesUtf.escape("a#b\\c#d=ef\\gh")), "a#b\\c#d=ef\\gh");
	}

	private String storeUtf(Map<String, String> map) {
		StringBuilderOutputStream stream = new StringBuilderOutputStream();
		PropertiesUtf.store(map, stream);
		return stream.getResult();
	}

	private MapAssert<String, String> assertLoad(String input) {
		return Assertions.assertThat(PropertiesUtf.load(StreamTool.toInputStream(input)));
	}
}
