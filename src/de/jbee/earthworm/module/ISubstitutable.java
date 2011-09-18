package de.jbee.earthworm.module;

public interface ISubstitutable<T> {

	String id();

	IRenderInstructor<? super T> substitute( String occurance );
}
