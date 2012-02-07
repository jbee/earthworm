package de.jbee.earthworm.module;

import de.jbee.data.Data;
import de.jbee.data.DataProperty.ObjectProperty;
import de.jbee.data.DataProperty.RangeProperty;
import de.jbee.data.DataProperty.ValueProperty;
import de.jbee.earthworm.process.ControlCycle;
import de.jbee.earthworm.process.MarkupCycle;

public final class BaseMarkup {

	private BaseMarkup() {
		// util class
	}

	public static final RenderInstructor<Object> EMPTY = new EmptyMarkup();

	public static <T> RenderInstructor<T> dynamic( ValueProperty<? super T, ? extends CharSequence> path ) {
		return new DynamicMarkup<T>( path );
	}

	public static RenderInstructor<Object> constant( CharSequence markup ) {
		return new StaticMarkup( markup );
	}

	public static <T, V> RenderInstructor<T> partial( ObjectProperty<? super T, V> path,
			RenderInstructor<V> part ) {
		return new PartialMarkup<T, V>( path, part );
	}

	public static <T> RenderInstructor<T> conditional( Conditional<? super T> condition,
			RenderInstructor<? super T> markup ) {
		return new ConditionalMarkup<T>( condition, markup );
	}

	public static <T, E> RenderInstructor<T> repeat( RangeProperty<T, E> path,
			RenderInstructor<E> element ) {
		return new RepeatingMarkup<T, E>( path, element );
	}

	static final class EmptyMarkup
			implements RenderInstructor<Object> {

		@Override
		public void instructRendering( Data<? extends Object> data, ControlCycle cycle ) {
			// just do nothing
		}

		@Override
		public String toString() {
			return "·";
		}

	}

	static final class MarkupInstructor<T>
			implements RenderInstructor<T> {

		private final Markup<? super T> markup;

		MarkupInstructor( Markup<? super T> markup ) {
			super();
			this.markup = markup;
		}

		@Override
		public void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			cycle.render( data, markup );
		}

		@Override
		public String toString() {
			return markup.toString();
		}
	}

	/**
	 * Plays a double role: It is a {@link RenderInstructor} to instructing to render itself. Than
	 * it acts as the {@link Markup} to render.
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static abstract class DirectRenderingMarkup<T>
			implements RenderInstructor<T>, Markup<T> {

		@Override
		public final void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			cycle.render( data, this );
		}
	}

	static final class DynamicMarkup<T>
			extends DirectRenderingMarkup<T> {

		private final ValueProperty<? super T, ? extends CharSequence> path;

		DynamicMarkup( ValueProperty<? super T, ? extends CharSequence> path ) {
			super();
			this.path = path;
		}

		@Override
		public void render( Data<? extends T> data, MarkupCycle cycle ) {
			cycle.stream().append( cycle.read( data, path ) );
		}

		@Override
		public String toString() {
			return "{" + path.toString() + "}";
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
		public void render( Data<? extends Object> data, MarkupCycle cycle ) {
			cycle.stream().append( markup );
		}

		@Override
		public String toString() {
			return markup.toString();
		}
	}

	static final class ConditionalMarkup<T>
			implements RenderInstructor<T> {

		private final Conditional<? super T> condition;
		private final RenderInstructor<? super T> item;

		ConditionalMarkup( Conditional<? super T> condition, RenderInstructor<? super T> item ) {
			super();
			this.condition = condition;
			this.item = item;
		}

		@Override
		public void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			cycle.guard( data, condition, item );
		}

		@Override
		public String toString() {
			return "[" + condition.toString() + " ? " + item.toString() + "]";
		}
	}

	static final class RepeatingMarkup<T, E>
			implements RenderInstructor<T> {

		private final RangeProperty<T, E> path;
		private final RenderInstructor<E> item;

		RepeatingMarkup( RangeProperty<T, E> path, RenderInstructor<E> item ) {
			super();
			this.path = path;
			this.item = item;
		}

		@Override
		public void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			cycle.repeat( data.subs( path ), item );
		}

		@Override
		public String toString() {
			return "(" + path.toString() + " * " + item.toString() + ")";
		}
	}

	static class PartialMarkup<T, V>
			implements RenderInstructor<T> {

		private final ObjectProperty<? super T, V> path;
		private final RenderInstructor<V> part;

		PartialMarkup( ObjectProperty<? super T, V> path, RenderInstructor<V> part ) {
			super();
			this.path = path;
			this.part = part;
		}

		@Override
		public void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			part.instructRendering( data.sub( path ), cycle );
		}

		@Override
		public String toString() {
			return "(" + path.toString() + " > " + part.toString() + ")";
		}
	}
}
