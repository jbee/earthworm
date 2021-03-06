package de.jbee.earthworm.module;

/**
 * A {@link Container} is the nesting block element of the preparation phase.
 * 
 * The task of a {@linkplain Container} is to adapt/encapsulate one or more
 * {@link RenderInstructor}s that operate well inside of it to the outside so they can be inserted
 * into the outside list of elements to render.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 * 
 * @param <T>
 *            The type of element are prepared outside of the container
 * @param <E>
 *            The type of element can be prepared inside the container
 */
public interface Container<T, E> {

	RenderInstructor<T> encapsulate( RenderInstructor<E> element );

}
