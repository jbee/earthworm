package de.jbee.earthworm.process;

import de.jbee.data.Dataset;
import de.jbee.data.Dataset.ValueProperty;

public interface RenderCycle {

	<T> T util( UtilFactory<T> factory );

	<T, V> V read( Dataset<? extends T> dataset, ValueProperty<? super T, ? extends V> value );

	RenderStream stream();
}
