package com.eriklievaart.toolkit.lang.api.str.printer;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.str.printer.ColumnPrinter;

public class ColumnPrinterU {

	@Test
	public void setGap() {
		ColumnPrinter printer = new ColumnPrinter();
		printer.addRow(1, 2);

		printer.setVerticalGap(0);
		CheckStr.isEqual(printer.toString(), "12\n");

		printer.setVerticalGap(1);
		CheckStr.isEqual(printer.toString(), "1 2\n");

		printer.setVerticalGap(4);
		CheckStr.isEqual(printer.toString(), "1    2\n");
	}

	@Test
	public void setAlignLeft() {
		ColumnPrinter printer = new ColumnPrinter();
		printer.addRow(1, 2);
		printer.addRow(11, 12);

		printer.setVerticalGap(0);
		printer.setAlignLeft();
		CheckStr.isEqual(printer.toString(), "1 2 \n1112\n");
	}

	@Test
	public void setAlignRight() {
		ColumnPrinter printer = new ColumnPrinter();
		printer.addRow(1, 2);
		printer.addRow(11, 12);

		printer.setVerticalGap(0);
		printer.setAlignRight();
		CheckStr.isEqual(printer.toString(), " 1 2\n1112\n");
	}
}