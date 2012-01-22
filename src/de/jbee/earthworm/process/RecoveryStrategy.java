package de.jbee.earthworm.process;

public final class RecoveryStrategy {

	private RecoveryStrategy() {
		throw new UnsupportedOperationException( "util" );
	}

	public static IRecoveryStrategy stripOut() {
		return new StreamStrippingRecoveryStrategy();
	}

	/**
	 * Removes those parts of the stream rendered by the faulty element (and maybe their parents).
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static final class StreamStrippingRecoveryStrategy
			implements IRecoveryStrategy {

		private StreamMark start;

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
