package de.jbee.earthworm.data;

import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IListPath;
import de.jbee.earthworm.data.IData.IValuePath;

public class Path {

	static class DataPath<R, P, T>
			implements IDataPath<R, T> {

		private final String name;
		private final IDataPath<R, P> parent;

		DataPath( String name, IDataPath<R, P> parent ) {
			super();
			this.name = name;
			this.parent = parent;
		}

		@Override
		public <S> IDataPath<R, S> dot( IDataPath<T, S> subpath ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <V> IValuePath<R, V> dot( IValuePath<T, V> subpath ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <E> IListPath<R, E> dot( IListPath<T, E> subpath ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IListPath<R, T> repeat( int times ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
