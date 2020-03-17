package com.eriklievaart.toolkit.xml.api;

import java.io.IOException;
import java.io.Reader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.eriklievaart.toolkit.convert.api.Converters;
import com.eriklievaart.toolkit.lang.api.concurrent.RaceCondition;

/**
 * This class can be used to read XML files and convert them into Objects. This class is essentially a wrapper over SAX
 * that uses processing instructions to determine how to create Objects through reflection. When a
 * {@link SaxReflectionReader} instance is created a prefix is passed to the constructor. This prefix is used to
 * identify processing instructions for the {@link SaxReflectionReader}. Valid processing instructions are made using
 * the prefix in combination with one of the suffixes "element" or "close".
 *
 * Here is a very basic example:
 *
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;?prefix.element panel=javax.swing.JPanel?&gt;
 * &lt;?prefix.element button=javax.swing.JButton?&gt;
 * &lt;?prefix.close add(java.awt.Component)?&gt;
 * &lt;panel&gt;
 *     &lt;button text="label" horizontalTextPosition="4" /&gt;
 * &lt;/panel&gt;
 * </pre>
 *
 * This example creates a panel of type javax.swing.JPanel and adds to it a child of type javax.swing.JButton. The
 * button has "label" as text and the horizontalTextPosition attribute is set to "4".
 * <p/>
 * For every distinct class you want to create instances of, a processing instruction of the type element is required.
 * The value of the processing instruction is the name of the element in the XML file followed by an equals sign and
 * then the class literal to create for this element (must have a no arg constructor). So, in the example above the
 * processing instruction "&lt;%prefix.element panel=javax.swing.JPanel%&gt;" will create an Object of type
 * javax.swing.JPanel for every occurrence of a panel element in the XML file. Any attributes in the XML file will be
 * called as properties on the instance. The values of the XML attributes will be converted using the Converters
 * registered with the in the constructor of the {@link SaxReflectionReader}.
 * <p>
 * You are also required to add a processing instruction of type close. This element specifies which method to call on a
 * parent element, when a child has been processed. So in the example above
 * "&lt;?prefix.close add(java.awt.Component)?&gt;" specifies that when the button has been created, is will be added to
 * the panel using the add method which takes a component as an argument.
 *
 * @author Erik Lievaart
 */
@RaceCondition("This class is absolutely not Thread safe!")
@SuppressWarnings("unchecked")
public class SaxReflectionReader {

	private final SaxReflectionHandler handler;

	/**
	 * Create a SaxReflectionReader.
	 *
	 * @param processingPrefix
	 *            look for processing instructions with the specified prefix.
	 * @param converters
	 *            XML attributes will be called as javabean properties on the instance. Converters will convert the XML
	 *            string value into the property type.
	 */
	public SaxReflectionReader(final String processingPrefix, final Converters converters) {
		this.handler = new SaxReflectionHandler(processingPrefix, converters);
	}

	/**
	 * Read an XML file found at the specified path.
	 *
	 * @see XMLReader#parse(String)
	 */
	public <E> E readXmlFile(final String file) throws SAXException, IOException {
		createReader().parse(file);
		return (E) handler.getResult();
	}

	/**
	 * Read an XML file found at the specified path.
	 *
	 * @see XMLReader#parse(InputSource)
	 */
	public <E> E readXmlFile(final Reader reader) throws IOException, SAXException {
		createReader().parse(new InputSource(reader));
		return (E) handler.getResult();
	}

	private XMLReader createReader() throws SAXException {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(handler);
		return xr;
	}
}