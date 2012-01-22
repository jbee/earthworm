package de.jbee.earthworm.process;

public interface IRenderStream {

	StreamMark mark();

	void rewriteFrom( StreamMark marker );
}
