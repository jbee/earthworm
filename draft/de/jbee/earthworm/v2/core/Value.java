package de.jbee.earthworm.v2.core;

public interface Value<T> {

	void pass(Converter<T, ?> converter);
}
