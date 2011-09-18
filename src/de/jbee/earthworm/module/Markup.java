package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IListPath;
import de.jbee.earthworm.data.IData.IValuePath;
import de.jbee.earthworm.process.IControlCycle;
import de.jbee.earthworm.process.IMarkupCycle;

public final class Markup {

	private Markup() {
		// util class
	}

	public static final IRenderInstructor<Object> EMPTY = new EmptyMarkup();

	public static <T> IRenderInstructor<T> dynamic(
			IValuePath<? super T, ? extends CharSequence> path ) {
		return new DynamicMarkup<T>( path );
	}

	public static IRenderInstructor<Object> counter( int depth ) {
		return counter( depth, 0 );
	}

	public static IRenderInstructor<Object> counter( int depth, int offset ) {
		return new CounterMarkup( depth, offset );
	}

	public static IRenderInstructor<Object> constant( CharSequence markup ) {
		return new StaticMarkup( markup );
	}

	public static <T, V> IRenderInstructor<T> partial( IDataPath<? super T, V> path,
			IRenderInstructor<V> part ) {
		return new PartialMarkup<T, V>( path, part );
	}

	public static <T> IRenderInstructor<T> conditional( IConditional<? super T> condition,
			IRenderInstructor<? super T> markup ) {
		return new ConditionalMarkup<T>( condition, markup );
	}

	public static <T, E> IRenderInstructor<T> repeat( IListPath<T, E> path,
			IRenderInstructor<E> element ) {
		return new RepeatingMarkup<T, E>( path, element );
	}

	static final class EmptyMarkup
			implements IRenderInstructor<Object> {

		@Override
		public void instructRendering( IData<? extends Object> data, IControlCycle cycle ) {
			// just do nothing
		}

		@Override
		public String toString() {
			return "·";
		}

	}

	static final class MarkupInstructor<T>
			implements IRenderInstructor<T> {

		private final IMarkup<? super T> markup;

		MarkupInstructor( IMarkup<? super T> markup ) {
			super();
			this.markup = markup;
		}

		@Override
		public void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			cycle.render( data, markup );
		}

		@Override
		public String toString() {
			return markup.toString();
		}
	}

	/**
	 * Plays a double role: It is a {@link IRenderInstructor} to instructing to render itself. Than
	 * it acts as the {@link IMarkup} to render.
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static abstract class DirectRenderingMarkup<T>
			implements IRenderInstructor<T>, IMarkup<T> {

		@Override
		public final void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			cycle.render( data, this );
		}
	}

	static final class DynamicMarkup<T>
			extends DirectRenderingMarkup<T> {

		private final IValuePath<? super T, ? extends CharSequence> path;

		DynamicMarkup( IValuePath<? super T, ? extends CharSequence> path ) {
			super();
			this.path = path;
		}

		@Override
		public void render( IData<? extends T> data, IMarkupCycle cycle ) {
			cycle.stream().append( cycle.read( data, path ) );
		}

		@Override
		public String toString() {
			return "{" + path.toString() + "}";
		}
	}

	//TODO the hole counter thing seams not to be so clever - just a small thing to have the counter value rendered
	static final class CounterMarkup
			extends DirectRenderingMarkup<Object> {

		private final int depth;
		private final int offset;

		CounterMarkup( int depth, int offset ) {
			super();
			this.depth = depth;
			this.offset = offset;
		}

		@Override
		public void render( IData<? extends Object> data, IMarkupCycle cycle ) {
			cycle.stream().append( String.valueOf( cycle.counter( depth ) + offset ) );
		}
	}

	static final class StaticMarkup
			extends DirectRenderingMarkup<Object> {

		private final CharSequence markup;

		StaticMarkup( CharSequence markup ) {
			super();
			this.markup = markup;
		}

		@Override
		public void render( IData<? extends Object> data, IMarkupCycle cycle ) {
			cycle.stream().append( markup );
		}

		@Override
		public String toString() {
			return markup.toString();
		}
	}

	static final class ConditionalMarkup<T>
			implements IRenderInstructor<T> {

		private final IConditional<? super T> condition;
		private final IRenderInstructor<? super T> item;

		ConditionalMarkup( IConditional<? super T> condition, IRenderInstructor<? super T> item ) {
			super();
			this.condition = condition;
			this.item = item;
		}

		@Override
		public void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			cycle.guard( data, condition, item );
		}

		@Override
		public String toString() {
			return "[" + condition.toString() + " ? " + item.toString() + "]";
		}
	}

	static final class RepeatingMarkup<T, E>
			implements IRenderInstructor<T> {

		private final IListPath<T, E> path;
		private final IRenderInstructor<E> item;

		RepeatingMarkup( IListPath<T, E> path, IRenderInstructor<E> item ) {
			super();
			this.path = path;
			this.item = item;
		}

		@Override
		public void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			cycle.repeat( data.subs( path ), item );
		}

		@Override
		public String toString() {
			return "(" + path.toString() + " * " + item.toString() + ")";
		}
	}

	static class PartialMarkup<T, V>
			implements IRenderInstructor<T> {

		private final IDataPath<? super T, V> path;
		private final IRenderInstructor<V> part;

		PartialMarkup( IDataPath<? super T, V> path, IRenderInstructor<V> part ) {
			super();
			this.path = path;
			this.part = part;
		}

		@Override
		public void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			part.instructRendering( data.sub( path ), cycle );
		}

		@Override
		public String toString() {
			return "(" + path.toString() + " > " + part.toString() + ")";
		}
	}
}
