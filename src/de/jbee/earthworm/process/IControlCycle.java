package de.jbee.earthworm.process;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.module.IConditional;
import de.jbee.earthworm.module.IMarkup;
import de.jbee.earthworm.module.IRenderInstructor;

public interface IControlCycle
		extends IRenderCycle {

	<T> void render( IData<T> data, IMarkup<? super T> content );

	<T> void instruct( IData<T> data, IRenderInstructor<? super T> content );

	<T> void instruct( IData<T> data, IRenderInstructor<? super T> content,
			IRecoveryStrategy strategy );

	<T> void repeat( Iterable<IData<T>> data, IRenderInstructor<? super T> content );

	<T> void guard( IData<T> data, IConditional<? super T> condition,
			IRenderInstructor<? super T> then );

	<T> void upon( IData<T> data, IConditional<? super T> condition,
			IRenderInstructor<? super T> then, IRenderInstructor<? super T> elSe );
}
