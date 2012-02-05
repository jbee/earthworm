package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Data;

public interface Conditional<T> {

	boolean fulfilledBy( Data<? extends T> data );
}
