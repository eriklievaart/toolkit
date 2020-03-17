package com.eriklievaart.toolkit.convert.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.convert.api.construct.BooleanConstructor;
import com.eriklievaart.toolkit.convert.api.construct.EnumConstructor;
import com.eriklievaart.toolkit.convert.api.construct.IntegerConstructor;
import com.eriklievaart.toolkit.convert.api.construct.LongConstructor;
import com.eriklievaart.toolkit.convert.api.construct.StringConstructor;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.FromCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.concurrent.ThreadSafe;

/**
 * Groups individual Converter's into a collection so that multiple types can be converted by a single Object.
 *
 * @author Erik Lievaart
 */
@ThreadSafe
public class Converters {
	private static final Map<Class<?>, Class<?>> PRIMITIVES = createPrimitiveMap();

	private static final List<Converter<?>> BASIC_CONVERTERS_LIST = Arrays.<Converter<?>> asList(
			new StringConstructor().createConverter(), new IntegerConstructor().createConverter(),
			new LongConstructor().createConverter(), new BooleanConstructor().createConverter());

	public static final Converters BASIC_CONVERTERS = new Converters(BASIC_CONVERTERS_LIST);

	private final Map<Class<?>, Converter<?>> converters = NewCollection.concurrentMap();

	public Converters(final Converter<?>... converters) {
		this(FromCollection.toList(converters));
	}

	public Converters(final Iterable<Converter<?>> converters) {
		for (Converter<?> converter : converters) {
			Check.notNull(converter);
			Check.notNull(converter.getValidator(), "Validator required!");
			Check.notTrue(PRIMITIVES.containsKey(converter.getLiteral()), "Register the wrapper instead");

			this.converters.put(converter.getLiteral(), converter);
		}
	}

	private static Map<Class<?>, Class<?>> createPrimitiveMap() {
		Map<Class<?>, Class<?>> primitiveToWrapper = NewCollection.map();

		primitiveToWrapper.put(boolean.class, Boolean.class);
		primitiveToWrapper.put(short.class, Short.class);
		primitiveToWrapper.put(int.class, Integer.class);
		primitiveToWrapper.put(long.class, Long.class);
		primitiveToWrapper.put(float.class, Float.class);
		primitiveToWrapper.put(double.class, Double.class);
		primitiveToWrapper.put(char.class, Character.class);

		return primitiveToWrapper;
	}

	/**
	 * Convert the specified String value to the specified Type.
	 *
	 * @throws AssertionException
	 *             if there is no Converter for the specified Type.
	 * @throws ConversionException
	 *             if there is a converter, but the value is not valid for the converter.
	 */
	@SuppressWarnings("unchecked")
	public <T> T to(final Class<T> type, final String value) throws ConversionException {
		AssertionException.on(!isConvertible(type), "Missing Converter, cannot convert type: %", type);
		return (T) getConverter(type).convertToObject(value);
	}

	/**
	 * Is there a converter for the specified Type?
	 */
	public boolean isConvertible(final Class<?> type) {
		return getConverter(type) != null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Converter<?> getConverter(final Class<?> type) {
		if (PRIMITIVES.containsKey(type)) {
			return converters.get(PRIMITIVES.get(type));
		}
		if (type.isEnum() && !converters.containsKey(type)) {
			return new EnumConstructor(type).createConverter();
		}
		return converters.get(type);
	}

	/**
	 * List all the Converter Objects wrapper by this Converters instance.
	 */
	public Collection<Converter<?>> listConverters() {
		return converters.values();
	}

	/**
	 * List all the Type's that can be converted by this Converters instance.
	 */
	public Collection<Class<?>> getTypes() {
		return converters.keySet();
	}

	/**
	 * Convert the supplied instance to a String using the converter found using the type of the Object.
	 *
	 * @param object
	 *            cannot be null.
	 * @return a String presentation of the object.
	 * @throws ConversionException
	 *             if it could not be converted.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String convertToString(Object object) throws ConversionException {
		Check.notNull(object);

		Class<?> type = object.getClass();
		Converter converter = getConverter(type);
		Check.notNull(converter, "Missing converter: $", type);

		return converter.convertToString(object);
	}
}