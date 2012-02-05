package de.jbee.earthworm.process;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.module.Conditional;
import de.jbee.earthworm.module.Markup;
import de.jbee.earthworm.module.RenderInstructor;

public interface ControlCycle
		extends RenderCycle {

	<T> void render( Data<T> data, Markup<? super T> content );

	<T> void instruct( Data<T> data, RenderInstructor<? super T> content );

	<T> void instruct( Data<T> data, RenderInstructor<? super T> content,
			RecoveryStrategy strategy );

	<T> void repeat( Iterable<Data<T>> data, RenderInstructor<? super T> content );

	<T> void guard( Data<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then );

	<T> void upon( Data<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then, RenderInstructor<? super T> elSe );
}
