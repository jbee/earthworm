package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.data.Data.ValuePath;

public class Fulfills {

	public static <T> Conditional<T> trueValue( ValuePath<T, Boolean> path ) {
		return booleanValue( path, true );
	}

	public static <T> Conditional<T> falseValue( ValuePath<T, Boolean> path ) {
		return booleanValue( path, false );
	}

	public static <T> Conditional<T> booleanValue( ValuePath<T, Boolean> path, boolean expected ) {
		return new BooleanValueCondition<T>( path, false );
	}

	/**
	 * The boolean value given through the path has the expected value.
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static final class BooleanValueCondition<T>
			implements Conditional<T> {

		private final ValuePath<T, Boolean> path;
		private final boolean expected;

		BooleanValueCondition( ValuePath<T, Boolean> path, boolean expected ) {
			super();
			this.path = path;
			this.expected = expected;
		}

		@Override
		public boolean fulfilledBy( Data<? extends T> data ) {
			return data.value( path ) == expected;
		}
	}
}
