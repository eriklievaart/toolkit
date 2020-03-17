package com.eriklievaart.toolkit.io.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class StreamTool {

	public static void copyStream(InputStream input, OutputStream output) {
		try {
			try {
				copyStreamNoClose(input, output);
			} finally {
				close(input);
			}
		} finally {
			close(output);
		}
	}

	public static void copyStreamNoClose(InputStream input, OutputStream output) {
		try {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static List<String> readLines(InputStream is) {
		try {
			List<String> lines = NewCollection.list();

			try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
				String line = null;
				while ((line = in.readLine()) != null) {
					lines.add(line);
				}
			}
			return lines;

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			close(is);
		}
	}

	public static String toString(InputStream is) {
		try (Scanner scanner = new Scanner(is)) {
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}
	}

	public static String toString(InputStream is, String encoding) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamTool.copyStream(is, baos);
			return baos.toString(encoding);

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static String toString(Reader reader) {
		try {
			BufferedReader in = new BufferedReader(reader);
			StringBuilder result = new StringBuilder();

			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			close(reader);
		}
	}

	public static InputStream toInputStream(String data) {
		try {
			return new ByteArrayInputStream(data.getBytes("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void writeString(String data, OutputStream os) {
		try (PrintWriter writer = new PrintWriter(os)) {
			writer.write(data);
		}
	}

	public static void close(Closeable value) {
		try {
			if (value != null) {
				value.close();
			}
		} catch (IOException ignore) {
		}
	}

	public static void writeLines(Collection<String> lines, OutputStream out) {
		try {
			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out))) {
				for (String line : lines) {
					bw.write(line);
					bw.newLine();
				}
			}

		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}
}