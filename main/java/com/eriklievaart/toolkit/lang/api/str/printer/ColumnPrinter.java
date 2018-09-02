package com.eriklievaart.toolkit.lang.api.str.printer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class ColumnPrinter {

	private static final String NEW_LINE = "\n";
	private static final String BLANK = " ";
	private int vgap = 1;
	private ColumnPrinterAlignment alignment = ColumnPrinterAlignment.RIGHT;

	private List<List<String>> data = NewCollection.list();
	private int[] columnWidths = new int[0];

	public void addRow(Collection<?> row) {
		addRow(row.toArray());
	}

	public void addRow(Object... row) {
		Check.isTrue(row != null && row.length > 0, "Cannot add empty row!");
		if (columnWidths.length == 0) {
			columnWidths = new int[row.length];
		}
		int columns = columnWidths.length;
		Check.isTrue(columns == 0 || row.length == columns, "% columns in row, expecting %", row.length, columns);

		List<String> addRow = NewCollection.list();
		for (int column = 0; column < row.length; column++) {
			String value = row[column] == null ? "<null>" : row[column].toString().trim();
			addRow.add(value);
			if (value.length() > columnWidths[column]) {
				columnWidths[column] = value.length();
			}
		}
		data.add(addRow);
	}

	public void setVerticalGap(int gap) {
		this.vgap = gap;
	}

	public void setAlignLeft() {
		alignment = ColumnPrinterAlignment.LEFT;
	}

	public void setAlignRight() {
		alignment = ColumnPrinterAlignment.RIGHT;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (List<String> row : data) {
			builder.append(String.join(blanks(vgap), pad(row)));
			builder.append(NEW_LINE);
		}
		return builder.toString();
	}

	private List<String> pad(List<String> row) {
		List<String> padded = new ArrayList<>();
		for (int col = 0; col < columnWidths.length; col++) {
			padded.add(pad(row.get(col), col));
		}
		return padded;
	}

	private String pad(String value, int column) {
		String pad = blanks(columnWidths[column] - value.length());
		return alignment == ColumnPrinterAlignment.LEFT ? value + pad : pad + value;
	}

	private String blanks(int count) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			builder.append(BLANK);
		}
		return builder.toString();
	}

	public void dump() {
		System.out.println(toString());
	}
}
