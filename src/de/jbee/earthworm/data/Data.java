package de.jbee.earthworm.data;

import java.util.Collections;

import de.jbee.lang.Set;

public class Data {

	private static final IData<Object> EMPTY = new EmptyData<Object>();

	@SuppressWarnings ( "unchecked" )
	static <T> IData<T> empty() {
		return (IData<T>) EMPTY;
	}

	static final class Property {

		final String name;
		final Object value;

		Property( String name, Object value ) {
			super();
			this.name = name;
			this.value = value;
		}

	}

	/**
	 * <pre>
	 * .class CDBox
	 * disc1..class CD
	 * disc1.title 'Worst of ... CD1'
	 * </pre>
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 * 
	 */
	static final class ObjectData<T>
			implements IData<T> {

		private final String prefix;
		private final int start;
		private final int end;
		private final Set<Property> properties;

		ObjectData( String prefix, int start, int end, Set<Property> properties ) {
			super();
			this.prefix = prefix;
			this.start = start;
			this.end = end;
			this.properties = properties;
		}

		@Override
		public <S> IData<S> sub( IDataPath<? super T, S> path ) {
			// TODO Auto-generated method stub
			return null;
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