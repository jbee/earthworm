package de.jbee.earthworm.process;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IValuePath;
import de.jbee.earthworm.module.IConditional;
import de.jbee.earthworm.module.IMarkup;
import de.jbee.earthworm.module.IRenderInstructor;

public class RecoverRenderCycle
		implements IControlCycle {

	//TODO der cycle könnte evtl. auswerten ob neue recover startegien gesetzt wurden, seit dem letzten aktivieren try-catch
	// dann ist es glaube ich nutzlos ein weiteres darum zu wickeln bzw. kommt drauf an, ob man immer möglicht bald recovern will

	private final List<IRecoveryStrategy> strategies = new LinkedList<IRecoveryStrategy>();
	private final LinkedList<Object> hierarchy = new LinkedList<Object>();

	private final IControlCycle cycle;

	RecoverRenderCycle( IControlCycle cycle ) {
		super();
		this.cycle = cycle;
	}

	@Override
	public <T> void instruct( IData<T> data, IRenderInstructor<? super T> content,
			IRecoveryStrategy strategy ) {
		strategies.add( 0, strategy );
		instruct( data, content );
		strategies.remove( strategy );
	}

	@Override
	public <T> void guard( IData<T> data, IConditional<? super T> condition,
			IRenderInstructor<? super T> then ) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void instruct( IData<T> data, IRenderInstructor<? super T> content ) {
		hierarchy.addLast( content );
		try {
			cycle.instruct( data, content );
		} catch ( Exception e ) {
			recoverFrom( e );
		}
		hierarchy.removeLast();
	}

	@Override
	public <T, V> V read( IData<? extends T> data, IValuePath<? super T, ? extends V> path ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void render( IData<T> data, IMarkup<? super T> content ) {
		cycle.render( data, content );
	}

	@Override
	public <T> void repeat( Iterable<IData<T>> data, IRenderInstructor<? super T> content ) {
		cycle.repeat( data, content );
	}

	@Override
	public IRenderStream stream() {
		return cycle.stream();
	}

	@Override
	public <T> void upon( IData<T> data, IConditional<? super T> condition,
			IRenderInstructor<? super T> then, IRenderInstructor<? super T> elSe ) {
		try {
			cycle.upon( data, condition, then, elSe );
		} catch ( Exception e ) {
			recoverFrom( e );
		}
	}

	@Override
	public <T> T util( IUtilFactory<T> factory ) {
		// TODO Auto-generated method stub
		return null;
	}

	private void recoverFrom( Exception e ) {
		RenderException exception = new RenderException( e, hierarchy.toArray() );
		Iterator<IRecoveryStrategy> i = strategies.iterator();
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
