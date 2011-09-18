package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IValuePath;

public interface IPreparationCycle<T> {

	void constant( CharSequence markup );

	void dynamic( IValuePath<? super T, ? extends CharSequence> path );

	<V> void prepare( IDataPath<? super T, V> path, IComponent<V> part );

	<E> IPreparationCycle<E> in( IContainer<? super T, E> container );
}
