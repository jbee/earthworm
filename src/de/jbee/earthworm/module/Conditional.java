package de.jbee.earthworm.module;

import de.jbee.data.Dataset;

public interface Conditional<T> {

	boolean fulfilledBy( Dataset<? extends T> data );
}
