/**
 * 
 */
package de.jbee.earthworm.data;

public interface Path<R, T> {

	String name(); //OPEN vielleicht so bauen, dass der ListPath hier überschreiben kann um irgendwie eine Sequenz der einzelnen Namen zu liefern

	public static interface PseudoPath<R, T> {
		// etwa 1. element eines ListPath

		// für counter: addition oder subtraktion eines fixen betrags auf ein counter 
	}

	public static interface ListPath<R, T>
			extends DataPath<R, T> {

		DataPath<R, T> head();

		DataPath<R, T> at( int index );

		ListPath<R, T> range( int start, int end );

		ListPath<R, T> limit( int maxlength );
	}

	public static interface DataPath<R, T>
			extends Path<R, T> {

		//IData<T> dig( IData<? extends R> data );

		ListPath<R, T> repeat( int times );

		<S> DataPath<R, S> dot( DataPath<T, S> subpath );

		<V> ValuePath<R, V> dot( ValuePath<T, V> subpath );

		<E> ListPath<R, E> dot( ListPath<T, E> subpath );
	}

	public static interface ValuePath<R, T>
			extends Path<R, T> {

		//TODO sowas wie: <T> defaultValue();

		// TODO und sowas wie: T value(IData<? extends R> data);

		<V> NotionalPath<R, V> dot( NotionalPath<T, V> subpath );
	}

	public static interface NotionalPath<R, T>
			extends ValuePath<R, T> {

		T compute( R value );
	}

}