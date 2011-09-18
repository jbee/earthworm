package de.jbee.earthworm.process;

public interface IRecoveryStrategy {

	void establish( IControlCycle cycle );

	void recoverFrom( RenderException exception, IControlCycle cycle );

}
