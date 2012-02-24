package de.jbee.earthworm.module;

import de.jbee.data.Dataset;
import de.jbee.earthworm.process.ControlCycle;

public interface RenderInstructor<T> {

	void instructRendering( Dataset<? extends T> data, ControlCycle cycle );

}
