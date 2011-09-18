package de.jbee.earthworm.module;

/**
 * A {@link IComponent} is the basic element of a page, before it has been prepared.
 * 
 * During preparation the {@linkplain IComponent}s transform its content to equivalent
 * {@link IRenderInstructor}s.
 * 
 * The bigger brother of a {@linkplain IComponent} is a {@link IContainer}. They introduce levels of
 * nesting to the preparation cycle whereby they are the block elements of the preparation phase.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 * 
 * @see IContainer
 * 
 * @param <T>
 *            Type of value that can be prepared - A instance of that type is not accessible here
 *            since the preparation phase is realized once in a static context.
 */
public interface IComponent<T> {

	void prepare( IPreparationCycle<? extends T> cycle );

}
