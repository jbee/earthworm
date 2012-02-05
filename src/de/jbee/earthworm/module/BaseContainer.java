package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.data.Data.ListPath;
import de.jbee.earthworm.process.ControlCycle;
import de.jbee.earthworm.process.RecoveryStrategy;
import de.jbee.earthworm.process.BaseRecoveryStrategy;

public class BaseContainer {

	public static <T, E> Container<T, E> repeats( ListPath<T, E> path ) {
		return new RepeatingContainer<T, E>( path );
	}

	public static <T> Container<T, T> dependsOn( Conditional<? super T> condition ) {
		return new ConditionalContainer<T>( condition );
	}

	public static <T> Container<T, T> recoversByStrippingOut() {
		return recoversBy( BaseRecoveryStrategy.stripOut() );
	}

	public static <T> Container<T, T> recoversBy( RecoveryStrategy strategy ) {
		return new ExceptionRecoveringContainer<T>( strategy );
	}

	static final class RepeatingContainer<T, E>
			implements Container<T, E> {

		private final ListPath<T, E> path;

		RepeatingContainer( ListPath<T, E> path ) {
			super();
			this.path = path;
		}

		@Override
		public RenderInstructor<T> encapsulate( RenderInstructor<E> element ) {
			return BaseMarkup.repeat( path, element );
		}

	}

	static final class ConditionalContainer<T>
			implements Container<T, T> {

		private final Conditional<? super T> condition;

		ConditionalContainer( Conditional<? super T> condition ) {
			super();
			this.condition = condition;
		}

		@Override
		public RenderInstructor<T> encapsulate( RenderInstructor<T> element ) {
			return BaseMarkup.conditional( condition, element );
		}

	}

	static final class ExceptionRecoveringContainer<T>
			implements Container<T, T>, RenderInstructor<T> {

		private final RecoveryStrategy strategy;

		private RenderInstructor<T> element;

		ExceptionRecoveringContainer( RecoveryStrategy strategy ) {
			super();
			this.strategy = strategy;
		}

		@Override
		public RenderInstructor<T> encapsulate( RenderInstructor<T> element ) {
			this.element = element;
			return this;
		}

		@Override
		public void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			cycle.instruct( data, element, strategy );
		}

	}
}
