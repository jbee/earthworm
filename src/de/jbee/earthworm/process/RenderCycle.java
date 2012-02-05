package de.jbee.earthworm.process;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.data.Path.ValuePath;

public interface RenderCycle {

	<T> T util( UtilFactory<T> factory );

	<T, V> V read( Data<? extends T> data, ValuePath<? super T, ? extends V> path );

	int counter( int depth );

	RenderStream stream();
}
