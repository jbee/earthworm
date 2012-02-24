package de.jbee.earthworm.process;

import de.jbee.data.Dataset;
import de.jbee.data.Dataset.ValueProperty;
import de.jbee.earthworm.module.Conditional;
import de.jbee.earthworm.module.Markup;
import de.jbee.earthworm.module.RenderInstructor;
import de.jbee.lang.List;

public class RecoverRenderCycle
		implements ControlCycle {

	//TODO der cycle könnte evtl. auswerten ob neue recover startegien gesetzt wurden, seit dem letzten aktivieren try-catch
	// dann ist es glaube ich nutzlos ein weiteres darum zu wickeln bzw. kommt drauf an, ob man immer möglicht bald recovern will

	private List<RecoveryStrategy> strategies = List.with.noElements();
	private List<Object> hierarchy = List.with.noElements();

	private final ControlCycle cycle;

	RecoverRenderCycle( ControlCycle cycle ) {
		super();
		this.cycle = cycle;
	}

	@Override
	public <T> void instruct( Dataset<T> data, RenderInstructor<? super T> content,
			RecoveryStrategy strategy ) {
		strategies = strategies.prepand( strategy );
		instruct( data, content );
		strategies = strategies.drop( 1 );
	}

	@Override
	public <T> void guard( Dataset<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then ) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void instruct( Dataset<T> data, RenderInstructor<? super T> content ) {
		hierarchy = hierarchy.append( content );
		try {
			cycle.instruct( data, content );
		} catch ( Exception e ) {
			recoverFrom( e );
		}
		hierarchy = List.alterBy.dropRight( 1 ).from( hierarchy );
	}

	@Override
	public <T, V> V read( Dataset<? extends T> data, ValueProperty<? super T, ? extends V> path ) {
		return cycle.read( data, path );
	}

	@Override
	public <T> void render( Dataset<T> data, Markup<? super T> content ) {
		cycle.render( data, content );
	}

	@Override
	public <T> void repeat( Dataset<T> data, RenderInstructor<? super T> content ) {
		cycle.repeat( data, content );
	}

	@Override
	public RenderStream stream() {
		return cycle.stream();
	}

	@Override
	public <T> void upon( Dataset<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then, RenderInstructor<? super T> elSe ) {
		try {
			cycle.upon( data, condition, then, elSe );
		} catch ( Exception e ) {
			recoverFrom( e );
		}
	}

	@Override
	public <T> T util( UtilFactory<T> factory ) {
		// TODO Auto-generated method stub
		return null;
	}

	private void recoverFrom( Exception e ) {
		RenderException exception = new RenderException( e, hierarchy );
		int i = 0;
		final int end = strategies.length();
		while ( !exception.isResolved() && i < end ) {
			strategies.at( i ).recoverFrom( exception, this );
		}
		if ( !exception.isResolved() ) {
			throw exception;
		}
	}

}
