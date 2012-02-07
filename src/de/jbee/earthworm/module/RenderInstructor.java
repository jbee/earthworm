package de.jbee.earthworm.module;

import de.jbee.data.Data;
import de.jbee.earthworm.process.ControlCycle;

public interface RenderInstructor<T> {

	void instructRendering( Data<? extends T> data, ControlCycle cycle );

}
