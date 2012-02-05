package de.jbee.earthworm.data;

import de.jbee.earthworm.data.Data.DataPath;
import de.jbee.earthworm.data.Data.ListPath;
import de.jbee.earthworm.data.Data.ValuePath;

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
}
