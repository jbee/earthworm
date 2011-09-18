package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IListPath;

public final class Component {

	private Component() {
		// util
	}

	public static IComponent<Object> markup( CharSequence markup ) {
		return new MarkupComponent( markup );
	}

	public static <T, E> IComponent<T> partial( IDataPath<? super T, E> path, IComponent<E> part ) {
		return new PartialComponent<T, E>( path, part );
	}

	public static <T, E> IComponent<T> repeats( IListPath<? super T, E> path, IComponent<E> item ) {
		return new RepeatingComponent<T, E>( path, item );
	}

	public static <T> IComponent<T> when( IConditional<? super T> condition,
			IComponent<? super T> element ) {
		return new ConditionalComponent<T>( condition, element );
	}

	static final class MarkupComponent
			implements IComponent<Object> {

		final CharSequence markup;

		MarkupComponent( CharSequence markup ) {
			super();
			this.markup = markup;
		}

		@Override
		public void prepare( IPreparationCycle<? extends Object> cycle ) {
			cycle.constant( markup );
		}

	}

	static final class PartialComponent<T, E>
			implements IComponent<T> {

		final IDataPath<? super T, E> path;
		final IComponent<E> part;

		PartialComponent( IDataPath<? super T, E> path, IComponent<E> part ) {
			super();
			this.path = path;
			this.part = part;
		}

		@Override
		public void prepare( IPreparationCycle<? extends T> cycle ) {
			cycle.prepare( path, part );
		}

	}

	static final class RepeatingComponent<T, E>
			implements IComponent<T> {

		final IListPath<? super T, E> path;
		final IComponent<E> item;

		RepeatingComponent( IListPath<? super T, E> path, IComponent<E> element ) {
			super();
			this.path = path;
			this.item = element;
		}

		@Override
		public void prepare( IPreparationCycle<? extends T> cycle ) {
			item.prepare( cycle.in( Container.repeats( path ) ) );
		}

	}

	static final class ConditionalComponent<T>
			implements IComponent<T> {

		final IConditional<? super T> condition;
		final IComponent<? super T> item;

		ConditionalComponent( IConditional<? super T> condition, IComponent<? super T> item ) {
			super();
			this.condition = condition;
			this.item = item;
		}

		@Override
		public void prepare( IPreparationCycle<? extends T> cycle ) {
			item.prepare( cycle.in( Container.dependsOn( condition ) ) );
		}

	}
}
