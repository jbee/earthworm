package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IListPath;
import de.jbee.earthworm.process.IControlCycle;
import de.jbee.earthworm.process.IRecoveryStrategy;
import de.jbee.earthworm.process.RecoveryStrategy;

public class Container {

	public static <T, E> IContainer<T, E> repeats( IListPath<T, E> path ) {
		return new RepeatingContainer<T, E>( path );
	}

	public static <T> IContainer<T, T> dependsOn( IConditional<? super T> condition ) {
		return new ConditionalContainer<T>( condition );
	}

	public static <T> IContainer<T, T> recoversByStrippingOut() {
		return recovers( RecoveryStrategy.stripOut() );
	}

	public static <T> IContainer<T, T> recovers( IRecoveryStrategy strategy ) {
		return new ExceptionRecoveringContainer<T>( strategy );
	}

	static final class RepeatingContainer<T, E>
			implements IContainer<T, E> {

		private final IListPath<T, E> path;

		RepeatingContainer( IListPath<T, E> path ) {
			super();
			this.path = path;
		}

		@Override
		public IRenderInstructor<T> encapsulate( IRenderInstructor<E> element ) {
			return Markup.repeat( path, element );
		}

	}

	static final class ConditionalContainer<T>
			implements IContainer<T, T> {

		private final IConditional<? super T> condition;

		ConditionalContainer( IConditional<? super T> condition ) {
			super();
			this.condition = condition;
		}

		@Override
		public IRenderInstructor<T> encapsulate( IRenderInstructor<T> element ) {
			return Markup.conditional( condition, element );
		}

	}

	static final class ExceptionRecoveringContainer<T>
			implements IContainer<T, T>, IRenderInstructor<T> {

		private final IRecoveryStrategy strategy;

		private IRenderInstructor<T> element;

		ExceptionRecoveringContainer( IRecoveryStrategy strategy ) {
			super();
			this.strategy = strategy;
		}

		@Override
		public IRenderInstructor<T> encapsulate( IRenderInstructor<T> element ) {
			this.element = element;
			return this;
		}

		@Override
		public void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			cycle.instruct( data, element, strategy );
		}

	}
}
