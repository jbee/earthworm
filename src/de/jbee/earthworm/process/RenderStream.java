package de.jbee.earthworm.process;

public interface RenderStream {

	StreamMark mark();

	void rewriteFrom( StreamMark marker );
}
