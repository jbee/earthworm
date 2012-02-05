package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.process.MarkupCycle;
import de.jbee.earthworm.process.MarkupStream;

/**
 * <p>
 * {@link Markup} is the low level element of which a page consists. They compute the final string
 * representation for the <code>data</code> passed to their {@link #render(Data, MarkupCycle)}
 * method and append it to the {@link MarkupStream} of the {@link MarkupCycle} given.
 * </p>
 * 
 * There is a limited range of different types of markup:
 * <ul>
 * <li>constant strings</li>
 * <li>strings computed directly from a single value</li>
 * </ul>
 * <p>
 * The "bigger" brother of the {@linkplain Markup} is the {@link RenderInstructor}.
 * </p>
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 * 
 * @see BaseMarkup
 * @see RenderInstructor
 * 
 * @param <T>
 *            The type of value that can be rendered by this markup element
 */
public interface Markup<T> {

	void render( Data<? extends T> data, MarkupCycle cycle );
}
