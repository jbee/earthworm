package de.jbee.earthworm.module;

public interface Substitutable<T> {

	String id();

	RenderInstructor<? super T> substitute( String occurance );
}
