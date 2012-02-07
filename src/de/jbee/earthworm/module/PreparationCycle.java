package de.jbee.earthworm.module;

import de.jbee.data.DataProperty.ObjectProperty;
import de.jbee.data.DataProperty.ValueProperty;

public interface PreparationCycle<T> {

	void constant( CharSequence markup );

	void variable( ValueProperty<? super T, ? extends CharSequence> path );

	<V> void prepare( ObjectProperty<? super T, V> path, Component<V> part );

	<E> PreparationCycle<E> in( Container<? super T, E> container );
}
