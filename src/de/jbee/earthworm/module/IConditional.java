package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;

public interface IConditional<T> {

	boolean fulfilledBy( IData<? extends T> data );
}
