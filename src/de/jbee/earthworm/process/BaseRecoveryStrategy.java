package de.jbee.earthworm.process;

public final class BaseRecoveryStrategy {

	private BaseRecoveryStrategy() {
		throw new UnsupportedOperationException( "util" );
	}

	public static RecoveryStrategy stripOut() {
		return new StreamStrippingRecoveryStrategy();
	}

	/**
	 * Removes those parts of the stream rendered by the faulty element (and maybe their parents).
	 * 
	 * @author Jan Bernitt (jan.bernitt@gmx.de)
	 */
	static final class StreamStrippingRecoveryStrategy
			implements RecoveryStrategy {

		private StreamMark start;

		@Override
		public void establish( ControlCycle cycle ) {
			start = cycle.stream().mark();
		}

		@Override
		public void recoverFrom( RenderException exception, ControlCycle cycle ) {
			cycle.stream().rewriteFrom( start );
			exception.markResolved();
		}

	}
}
