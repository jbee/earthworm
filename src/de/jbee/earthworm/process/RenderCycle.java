package de.jbee.earthworm.process;

import de.jbee.data.Data;
import de.jbee.data.DataProperty.ValueProperty;

public interface RenderCycle {

	<T> T util( UtilFactory<T> factory );

	<T, V> V read( Data<? extends T> data, ValueProperty<? super T, ? extends V> path );

	RenderStream stream();
}
