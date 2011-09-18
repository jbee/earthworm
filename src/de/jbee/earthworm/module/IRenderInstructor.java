package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.process.IControlCycle;

public interface IRenderInstructor<T> {

	void instructRendering( IData<? extends T> data, IControlCycle cycle );

}
