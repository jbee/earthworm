package de.jbee.earthworm.data;

public interface TypeDescriptor<T> {

	String name();

	Class<T> type();
}
