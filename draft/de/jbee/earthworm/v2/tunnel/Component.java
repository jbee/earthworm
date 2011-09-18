package de.jbee.earthworm.v2.tunnel;

import de.jbee.earthworm.v2.core.Cycle;

public interface Component<T> {

	void process(T value, Cycle cycle);
}
