package de.jbee.earthworm.module;

/**
 * A {@link Component} is the basic element of a page, before it has been prepared.
 * 
 * During preparation the {@linkplain Component}s transform its content to equivalent
 * {@link RenderInstructor}s.
 * 
 * The bigger brother of a {@linkplain Component} is a {@link Container}. They introduce levels of
 * nesting to the preparation cycle whereby they are the block elements of the preparation phase.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 * 
 * @see Container
 * 
 * @param <T>
 *            Type of value that can be prepared - A instance of that type is not accessible here
 *            since the preparation phase is realized once in a static context.
 */
public interface Component<T> {

	void prepare( PreparationCycle<? extends T> cycle );

}
