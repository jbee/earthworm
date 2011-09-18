package de.jbee.earthworm.process;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IValuePath;

public interface IRenderCycle {

	<T> T util( IUtilFactory<T> factory );

	<T, V> V read( IData<? extends T> data, IValuePath<? super T, ? extends V> path );

	int counter( int depth );

	IRenderStream stream();
}
