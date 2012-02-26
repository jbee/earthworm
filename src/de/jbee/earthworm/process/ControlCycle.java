package de.jbee.earthworm.process;

import de.jbee.data.Dataset;
import de.jbee.earthworm.module.Conditional;
import de.jbee.earthworm.module.Markup;
import de.jbee.earthworm.module.RenderInstructor;
import de.jbee.lang.Sequence;

public interface ControlCycle
		extends RenderCycle {

	<T> void render( Dataset<T> data, Markup<? super T> content );

	<T> void instruct( Dataset<T> data, RenderInstructor<? super T> content );

	<T> void instruct( Dataset<T> data, RenderInstructor<? super T> content,
			RecoveryStrategy strategy );

	<T> void repeat( Sequence<Dataset<T>> items, RenderInstructor<? super T> content );

	<T> void guard( Dataset<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then );

	<T> void upon( Dataset<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then, RenderInstructor<? super T> elSe );
}
