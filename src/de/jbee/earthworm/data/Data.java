package de.jbee.earthworm.data;

import de.jbee.earthworm.data.Path.DataPath;
import de.jbee.earthworm.data.Path.ListPath;
import de.jbee.earthworm.data.Path.NotionalPath;
import de.jbee.earthworm.data.Path.ValuePath;

public interface Data<T>
		extends TypeDescriptor<T> {

	<S> Data<S> sub( DataPath<? super T, S> path );

	<V> V value( ValuePath<? super T, V> path );

	<V> V value( NotionalPath<? super T, V> path );

	<S> Iterable<Data<S>> subs( ListPath<? super T, S> path );

	boolean isEmpty();

}
