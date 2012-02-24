package de.jbee.earthworm.module;

import de.jbee.data.Dataset;
import de.jbee.data.Dataset.ValueProperty;

public class Fulfills {

	public static <T> Conditional<T> trueValue( ValueProperty<T, Boolean> path ) {
		return booleanValue( path, true );
	}

	public static <T> Conditional<T> falseValue( ValueProperty<T, Boolean> path ) {
		return booleanValue( path, false );
	}

	public static <T> Conditional<T> booleanValue( ValueProperty<T, Boolean> path, boolean expected ) {
		return new BooleanValueCondition<T>( path, false );
	}

	/**
	 * The boolean value given through the path has the expected value.
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static final class BooleanValueCondition<T>
			implements Conditional<T> {

		private final ValueProperty<T, Boolean> path;
		private final boolean expected;

		BooleanValueCondition( ValueProperty<T, Boolean> path, boolean expected ) {
			super();
			this.path = path;
			this.expected = expected;
		}

		@Override
		public boolean fulfilledBy( Dataset<? extends T> data ) {
			return data.value( path ) == expected;
		}
	}
}
