package de.jbee.earthworm.data;

public interface ITypeDescriptor<T> {

	String name();

	Class<T> type();
}
