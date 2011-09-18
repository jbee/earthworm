package de.jbee.earthworm.v2.core;

public interface Converter<T, R> {

	R convert(T input);
}
