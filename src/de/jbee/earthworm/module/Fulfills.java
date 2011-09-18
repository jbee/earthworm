package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IValuePath;

public class Fulfills {

	public static <T> IConditional<T> trueValue( IValuePath<T, Boolean> path ) {
		return new TrueValueCondition<T>( path );
	}

	static final class TrueValueCondition<T>
			implements IConditional<T> {

		private final IValuePath<T, Boolean> path;

		TrueValueCondition( IValuePath<T, Boolean> path ) {
			super();
			this.path = path;
		}

		@Override
		public boolean fulfilledBy( IData<? extends T> data ) {
			return data.value( path );
		}
	}
}
