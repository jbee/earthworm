package de.jbee.earthworm.module;

import de.jbee.data.Dataset.MemberProperty;
import de.jbee.data.Dataset.ValueProperty;

public interface PreparationCycle<T> {

	void constant( CharSequence markup );

	void variable( ValueProperty<? super T, ? extends CharSequence> path );

	<V> void prepare( MemberProperty<? super T, V> path, Component<V> part );

	<E> PreparationCycle<E> in( Container<? super T, E> container );
}
