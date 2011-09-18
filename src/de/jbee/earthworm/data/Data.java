package de.jbee.earthworm.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Data {

	private static final IData<Object> EMPTY = new EmptyData<Object>();

	@SuppressWarnings ( "unchecked" )
	static <T> IData<T> empty() {
		return (IData<T>) EMPTY;
	}

	static final class HashData<T>
			implements IData<T> {

		private final String name;
		private final Class<T> type;
		private final Map<String, IData<?>> subs = new HashMap<String, IData<?>>();
		private final Map<String, Object> values = new HashMap<String, Object>();

		HashData( String name, Class<T> type ) {
			super();
			this.name = name;
			this.type = type;
		}

		@SuppressWarnings ( "unchecked" )
		@Override
		public <S> IData<S> sub( IDataPath<? super T, S> path ) {
			//FIXME S kann ja auch ein unterobjekt eines direkten subs sein - man muss also an den path delegieren und dieser dann zurück usw. 
			IData<?> subdata = subs.get( path.name() );
			return (IData<S>) ( subdata == null
				? Data.empty()
				: subdata );
		}

		@Override
		public <S> Iterable<IData<S>> subs( IListPath<? super T, S> path ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <V> V value( IValuePath<? super T, V> path ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <V> V value( INotionalPath<? super T, V> path ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Class<T> type() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	static final class EmptyData<T>
			implements IData<T> {

		@Override
		public <S> IData<S> sub( IDataPath<? super T, S> path ) {
			return empty();
		}

		@Override
		public <S> Iterable<IData<S>> subs( IListPath<? super T, S> path ) {
			return Collections.emptyList();
		}

		@Override
		public <V> V value( IValuePath<? super T, V> path ) {
			return null;
		}

		@Override
		public <V> V value( INotionalPath<? super T, V> path ) {
			return null;
		}

		@Override
		public String name() {
			return "";
		}

		@Override
		public Class<T> type() {
			return null;
		}

		@Override
		public String toString() {
			return "[nothing]";
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

	}
}