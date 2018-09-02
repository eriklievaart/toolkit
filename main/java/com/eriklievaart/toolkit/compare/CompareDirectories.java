package com.eriklievaart.toolkit.compare;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Runnable class that compares all the files in two directories.
 */
public class CompareDirectories {

	/**
	 * Compare the contents of two directories.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) throws IOException {
		JTextField from = new JTextField("/");
		JTextField to = new JTextField("/");

		JPanel panel = createPanel(from, to);
		int code = JOptionPane.showConfirmDialog(null, panel, "Compare directories", JOptionPane.OK_CANCEL_OPTION);
		if (code != JOptionPane.OK_OPTION) {
			return;
		}
		DirComparator comparator = new DirComparator(createFile(from.getText()), createFile(to.getText()));
		comparator.printChanges();
		System.out.println();
		comparator.printDiff();
	}

	private static JPanel createPanel(JTextField from, JTextField to) {
		Border line = BorderFactory.createLineBorder(Color.black);
		from.setBorder(BorderFactory.createTitledBorder(line, "Compare source"));
		to.setBorder(BorderFactory.createTitledBorder(line, "Compare destination"));

		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(from);
		panel.add(to);
		return panel;
	}

	private static File createFile(String path) {
		File file = new File(path);
		Check.isTrue(file.isDirectory(), "% is not a directory", path);
		return file;
	}

}
