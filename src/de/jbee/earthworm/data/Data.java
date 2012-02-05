package de.jbee.earthworm.data;

public interface Data<T>
		extends TypeDescriptor<T> {

	<S> Data<S> sub( DataPath<? super T, S> path );

	<V> V value( ValuePath<? super T, V> path );

	<V> V value( NotionalPath<? super T, V> path );

	<S> Iterable<Data<S>> subs( ListPath<? super T, S> path );

	boolean isEmpty();

	public static interface Path<R, T> {

		String name(); //OPEN vielleicht so bauen, dass der ListPath hier überschreiben kann um irgendwie eine Sequenz der einzelnen Namen zu liefern
	}

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

	static class StringLength
			implements NotionalPath<String, Integer> {

		@Override
		public Integer compute( String value ) {
			return value.length();
		}

		@Override
		public <V> NotionalPath<String, V> dot( NotionalPath<Integer, V> subpath ) {
			return new NotionalPathChain<String, Integer, V>( this, subpath );
		}

		@Override
		public String name() {
			return ":length";
		}

	}

	static class NotionalPathChain<R, I, T>
			implements NotionalPath<R, T> {

		private final NotionalPath<R, I> parent;
		private final NotionalPath<I, T> sub;

		NotionalPathChain( NotionalPath<R, I> parent, NotionalPath<I, T> sub ) {
			super();
			this.parent = parent;
			this.sub = sub;
		}

		@Override
		public T compute( R value ) {
			return sub.compute( parent.compute( value ) );
		}

		@Override
		public <V> NotionalPath<R, V> dot( NotionalPath<T, V> subpath ) {
			return new NotionalPathChain<R, T, V>( this, subpath );
		}

		@Override
		public String name() {
			return parent.name() + sub.name();
		}

	}

}
