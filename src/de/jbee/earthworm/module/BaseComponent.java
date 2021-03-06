package de.jbee.earthworm.module;

import de.jbee.data.Dataset.ItemProperty;
import de.jbee.data.Dataset.Items;
import de.jbee.data.Dataset.RecordProperty;

public final class BaseComponent {

	private BaseComponent() {
		// util
	}

	public static Component<Object> markup( CharSequence markup ) {
		return new MarkupComponent( markup );
	}

	public static <T, E> Component<T> partial( RecordProperty<? super T, E> path, Component<E> part ) {
		return new PartialComponent<T, E>( path, part );
	}

	public static <T, E> Component<T> repeats( ItemProperty<? super T, Items<E>> path,
			Component<E> item ) {
		return new RepeatingComponent<T, E>( path, item );
	}

	public static <T> Component<T> when( Conditional<? super T> condition,
			Component<? super T> element ) {
		return new ConditionalComponent<T>( condition, element );
	}

	static final class MarkupComponent
			implements Component<Object> {

		final CharSequence markup;

		MarkupComponent( CharSequence markup ) {
			super();
			this.markup = markup;
		}

		@Override
		public void prepare( PreparationCycle<? extends Object> cycle ) {
			cycle.constant( markup );
		}

	}

	static final class PartialComponent<T, E>
			implements Component<T> {

		final RecordProperty<? super T, E> path;
		final Component<E> part;

		PartialComponent( RecordProperty<? super T, E> path, Component<E> part ) {
			super();
			this.path = path;
			this.part = part;
		}

		@Override
		public void prepare( PreparationCycle<? extends T> cycle ) {
			cycle.prepare( path, part );
		}

	}

	static final class RepeatingComponent<T, E>
			implements Component<T> {

		final ItemProperty<? super T, Items<E>> path;
		final Component<E> item;

		RepeatingComponent( ItemProperty<? super T, Items<E>> path, Component<E> element ) {
			super();
			this.path = path;
			this.item = element;
		}

		@Override
		public void prepare( PreparationCycle<? extends T> cycle ) {
			item.prepare( cycle.in( BaseContainer.repeats( path ) ) );
		}

	}

	static final class ConditionalComponent<T>
			implements Component<T> {

		final Conditional<? super T> condition;
		final Component<? super T> item;

		ConditionalComponent( Conditional<? super T> condition, Component<? super T> item ) {
			super();
			this.condition = condition;
			this.item = item;
		}

		@Override
		public void prepare( PreparationCycle<? extends T> cycle ) {
			item.prepare( cycle.in( BaseContainer.dependsOn( condition ) ) );
		}

	}
}
