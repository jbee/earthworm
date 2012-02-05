package de.jbee.earthworm.data;

import de.jbee.earthworm.data.Path.DataPath;
import de.jbee.earthworm.data.Path.ListPath;
import de.jbee.earthworm.data.Path.NotionalPath;
import de.jbee.earthworm.data.Path.ValuePath;

public class BasePath {

	static class XDataPath<R, P, T>
			implements DataPath<R, T> {

		private final String name;
		private final DataPath<R, P> parent;

		XDataPath( String name, DataPath<R, P> parent ) {
			super();
			this.name = name;
			this.parent = parent;
		}

		@Override
		public <S> DataPath<R, S> dot( DataPath<T, S> subpath ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <V> ValuePath<R, V> dot( ValuePath<T, V> subpath ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <E> ListPath<R, E> dot( ListPath<T, E> subpath ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ListPath<R, T> repeat( int times ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return null;
		}

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
