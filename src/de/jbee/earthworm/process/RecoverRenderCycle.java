package de.jbee.earthworm.process;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.data.Data.ValuePath;
import de.jbee.earthworm.module.Conditional;
import de.jbee.earthworm.module.Markup;
import de.jbee.earthworm.module.RenderInstructor;

public class RecoverRenderCycle
		implements ControlCycle {

	//TODO der cycle könnte evtl. auswerten ob neue recover startegien gesetzt wurden, seit dem letzten aktivieren try-catch
	// dann ist es glaube ich nutzlos ein weiteres darum zu wickeln bzw. kommt drauf an, ob man immer möglicht bald recovern will

	private final List<RecoveryStrategy> strategies = new LinkedList<RecoveryStrategy>();
	private final LinkedList<Object> hierarchy = new LinkedList<Object>();

	private final ControlCycle cycle;

	RecoverRenderCycle( ControlCycle cycle ) {
		super();
		this.cycle = cycle;
	}

	@Override
	public <T> void instruct( Data<T> data, RenderInstructor<? super T> content,
			RecoveryStrategy strategy ) {
		strategies.add( 0, strategy );
		instruct( data, content );
		strategies.remove( strategy );
	}

	@Override
	public <T> void guard( Data<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then ) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void instruct( Data<T> data, RenderInstructor<? super T> content ) {
		hierarchy.addLast( content );
		try {
			cycle.instruct( data, content );
		} catch ( Exception e ) {
			recoverFrom( e );
		}
		hierarchy.removeLast();
	}

	@Override
	public <T, V> V read( Data<? extends T> data, ValuePath<? super T, ? extends V> path ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void render( Data<T> data, Markup<? super T> content ) {
		cycle.render( data, content );
	}

	@Override
	public <T> void repeat( Iterable<Data<T>> data, RenderInstructor<? super T> content ) {
		cycle.repeat( data, content );
	}

	@Override
	public RenderStream stream() {
		return cycle.stream();
	}

	@Override
	public <T> void upon( Data<T> data, Conditional<? super T> condition,
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
		RenderException exception = new RenderException( e, hierarchy.toArray() );
		Iterator<RecoveryStrategy> i = strategies.iterator();
		while ( !exception.isResolved() && i.hasNext() ) {
			i.next().recoverFrom( exception, this );
		}
		if ( !exception.isResolved() ) {
			throw exception;
		}
	}

	@Override
	public int counter( int depth ) {
		return cycle.counter( depth );
	}

}
