package de.jbee.earthworm.process;

public interface IRenderStream {

	StreamMarker mark();

	void rewriteFrom( StreamMarker marker );
}
