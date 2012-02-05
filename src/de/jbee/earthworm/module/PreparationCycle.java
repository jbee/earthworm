package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Path.DataPath;
import de.jbee.earthworm.data.Path.ValuePath;

public interface PreparationCycle<T> {

	void constant( CharSequence markup );

	void variable( ValuePath<? super T, ? extends CharSequence> path );

	<V> void prepare( DataPath<? super T, V> path, Component<V> part );

	<E> PreparationCycle<E> in( Container<? super T, E> container );
}
