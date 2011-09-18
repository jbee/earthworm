package de.jbee.earthworm.process;

public final class RecoveryStrategy {

	private RecoveryStrategy() {
		throw new UnsupportedOperationException( "util" );
	}

	public static IRecoveryStrategy stripOut() {
		return new StripStreamRecoveryStrategy();
	}

	static final class StripStreamRecoveryStrategy
			implements IRecoveryStrategy {

		private StreamMarker start;

		@Override
		public void establish( IControlCycle cycle ) {
			start = cycle.stream().mark();
		}

		@Override
		public void recoverFrom( RenderException exception, IControlCycle cycle ) {
			cycle.stream().rewriteFrom( start );
			exception.markResolved();
		}

	}
}
