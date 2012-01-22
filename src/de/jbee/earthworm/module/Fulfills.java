package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IValuePath;

public class Fulfills {

	public static <T> IConditional<T> trueValue( IValuePath<T, Boolean> path ) {
		return booleanValue( path, true );
	}

	public static <T> IConditional<T> falseValue( IValuePath<T, Boolean> path ) {
		return booleanValue( path, false );
	}

	public static <T> IConditional<T> booleanValue( IValuePath<T, Boolean> path, boolean expected ) {
		return new BooleanValueCondition<T>( path, false );
	}

	/**
	 * The boolean value given through the path has the expected value.
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static final class BooleanValueCondition<T>
			implements IConditional<T> {

		private final IValuePath<T, Boolean> path;
		private final boolean expected;

		BooleanValueCondition( IValuePath<T, Boolean> path, boolean expected ) {
			super();
			this.path = path;
			this.expected = expected;
		}

		@Override
		public boolean fulfilledBy( IData<? extends T> data ) {
			return data.value( path ) == expected;
		}
	}
}
