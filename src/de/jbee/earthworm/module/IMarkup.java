package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.process.IMarkupCycle;
import de.jbee.earthworm.process.IMarkupStream;

/**
 * <p>
 * {@link IMarkup} is the low level element of which a page consists. They compute the final string
 * representation for the <code>data</code> passed to their {@link #render(IData, IMarkupCycle)}
 * method and append it to the {@link IMarkupStream} of the {@link IMarkupCycle} given.
 * </p>
 * 
 * There is a limited range of different types of markup:
 * <ul>
 * <li>constant strings</li>
 * <li>strings computed directly from a single value</li>
 * </ul>
 * <p>
 * The "bigger" brother of the {@linkplain IMarkup} is the {@link IRenderInstructor}.
 * </p>
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 * 
 * @see Markup
 * @see IRenderInstructor
 * 
 * @param <T>
 *            The type of value that can be rendered by this markup element
 */
public interface IMarkup<T> {

	void render( IData<? extends T> data, IMarkupCycle cycle );
}
