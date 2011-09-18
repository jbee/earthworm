package de.jbee.earthworm.data;

public interface IData<T>
		extends ITypeDescriptor<T> {

	<S> IData<S> sub( IDataPath<? super T, S> path );

	<V> V value( IValuePath<? super T, V> path );

	<V> V value( INotionalPath<? super T, V> path );

	<S> Iterable<IData<S>> subs( IListPath<? super T, S> path );

	boolean isEmpty();

	//public static int

	public static interface IPath<R, T> {

		String name();
	}

	public static interface IPseudoPath<R, T> {
		// etwa 1. element eines ListPath

		// für counter: addition oder subtraktion eines fixen betrags auf ein counter 
	}

	public static interface IListPath<R, T>
			extends IDataPath<R, T> {

		IDataPath<R, T> first();

		IDataPath<R, T> at( int index );

		IListPath<R, T> range( int start, int end );

		IListPath<R, T> limit( int maxlength );
	}

	public static interface IDataPath<R, T>
			extends IPath<R, T> {

		//IData<T> dig( IData<? extends R> data );

		IListPath<R, T> repeat( int times );

		<S> IDataPath<R, S> dot( IDataPath<T, S> subpath );

		<V> IValuePath<R, V> dot( IValuePath<T, V> subpath );

		<E> IListPath<R, E> dot( IListPath<T, E> subpath );
	}

	public static interface IValuePath<R, T>
			extends IPath<R, T> {

		//TODO sowas wie: <T> defaultValue();

		// TODO und sowas wie: T value(IData<? extends R> data);

		<V> INotionalPath<R, V> dot( INotionalPath<T, V> subpath );
	}

	public static interface INotionalPath<R, T>
			extends IValuePath<R, T> {

		T compute( R value );
	}

	static class StringLength
			implements INotionalPath<String, Integer> {

		@Override
		public Integer compute( String value ) {
			return value.length();
		}

		@Override
		public <V> INotionalPath<String, V> dot( INotionalPath<Integer, V> subpath ) {
			return new NotionalPathChain<String, Integer, V>( this, subpath );
		}

		@Override
		public String name() {
			return ":length";
		}

	}

	static class NotionalPathChain<R, I, T>
			implements INotionalPath<R, T> {

		private final INotionalPath<R, I> parent;
		private final INotionalPath<I, T> sub;

		NotionalPathChain( INotionalPath<R, I> parent, INotionalPath<I, T> sub ) {
			super();
			this.parent = parent;
			this.sub = sub;
		}

		@Override
		public T compute( R value ) {
			return sub.compute( parent.compute( value ) );
		}

		@Override
		public <V> INotionalPath<R, V> dot( INotionalPath<T, V> subpath ) {
			return new NotionalPathChain<R, T, V>( this, subpath );
		}

		@Override
		public String name() {
			return parent.name() + sub.name();
		}

	}

}
