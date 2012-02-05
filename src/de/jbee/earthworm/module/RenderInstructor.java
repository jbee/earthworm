package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.process.ControlCycle;

public interface RenderInstructor<T> {

	void instructRendering( Data<? extends T> data, ControlCycle cycle );

}
