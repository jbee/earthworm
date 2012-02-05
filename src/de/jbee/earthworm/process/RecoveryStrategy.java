package de.jbee.earthworm.process;

public interface RecoveryStrategy {

	void establish( ControlCycle cycle );

	void recoverFrom( RenderException exception, ControlCycle cycle );

}
