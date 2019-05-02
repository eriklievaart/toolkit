package com.eriklievaart.toolkit.reflect.api;

import java.sql.Timestamp;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.mock.BombSquad;
import com.eriklievaart.toolkit.vfs.api.file.VirtualFileType;

public class ConvertToolU {

	@Test
	public void convertStringToString() {
		Check.isEqual(ConvertTool.convert("foo", String.class), "foo");
	}

	@Test
	public void convertStringToEnum() {
		Check.isEqual(ConvertTool.convert("file", VirtualFileType.class), VirtualFileType.FILE);
		Check.isEqual(ConvertTool.convert("directory", VirtualFileType.class), VirtualFileType.DIRECTORY);
	}

	@Test
	public void convertStringToInteger() {
		Check.isEqual(ConvertTool.convert("123", Integer.class), 123);
	}

	@Test
	public void convertIfIsAssignable() {
		DefaultListModel<String> model = new DefaultListModel<>();
		Check.isEqual(ConvertTool.convert(model, ListModel.class), model);
	}

	@Test
	public void convertNulltoNull() {
		Check.isNull(ConvertTool.convert(null, Long.class));
	}

	@Test
	public void convertLongtoLong() {
		Check.isEqual(ConvertTool.convert(1l, Long.class), 1l);
	}

	@Test
	public void convertLongtoPrimitive() {
		Check.isEqual(ConvertTool.convert(1l, long.class), 1l);
	}

	@Test
	public void convertLongtoInteger() {
		Check.isEqual(ConvertTool.convert(1l, Integer.class), 1);
	}

	@Test
	public void convertLongtoIntegerOverflow() {
		BombSquad.diffuse("overflow", () -> ConvertTool.convert(Integer.MAX_VALUE + 1l, Integer.class));
	}

	@Test
	public void convertLongtoInt() {
		Check.isEqual(ConvertTool.convert(1l, int.class), 1);
	}

	@Test
	public void convertLongtoIntOverflow() {
		BombSquad.diffuse("overflow", () -> ConvertTool.convert(Integer.MAX_VALUE + 1l, int.class));
	}

	@Test
	public void convertIntegerToLong() {
		Check.isEqual(ConvertTool.convert(1, Long.class), 1l);
	}

	@Test
	public void convertLongtoShort() {
		Check.isEqual(ConvertTool.convert(1l, short.class), (short) 1);
	}

	@Test
	public void convertLongtoShortOverflow() {
		BombSquad.diffuse("overflow", () -> ConvertTool.convert(Short.MAX_VALUE + 1l, short.class));
	}

	@Test
	public void convertLongtoByte() {
		Check.isEqual(ConvertTool.convert(1l, byte.class), (byte) 1);
	}

	@Test
	public void convertLongtoByteOverflow() {
		BombSquad.diffuse("overflow", () -> ConvertTool.convert(Byte.MAX_VALUE + 1l, byte.class));
	}

	@Test
	public void convertByteToLong() {
		Check.isEqual(ConvertTool.convert((byte) 1, long.class), 1l);
	}

	@Test
	public void convertLongtoThreadFail() {
		BombSquad.diffuse("Cannot convert Long to Thread", () -> ConvertTool.convert(1l, Thread.class));
	}

	@Test
	public void convertTimestamptoLong() {
		Check.isEqual(ConvertTool.convert(new Timestamp(150), Long.class), 150l);
	}
}
