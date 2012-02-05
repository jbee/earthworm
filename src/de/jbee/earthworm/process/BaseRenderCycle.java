package de.jbee.earthworm.process;

import java.util.LinkedList;

import de.jbee.earthworm.data.Data;
import de.jbee.earthworm.data.Data.ValuePath;
import de.jbee.earthworm.module.Conditional;
import de.jbee.earthworm.module.Markup;
import de.jbee.earthworm.module.RenderInstructor;
import de.jbee.earthworm.module.BaseMarkup;

public class BaseRenderCycle
		implements MarkupCycle, ControlCycle {

	private final ControlCycle cycle;

	private final LinkedList<Integer> counters = new LinkedList<Integer>();

	BaseRenderCycle() {
		this( null );
	}

	BaseRenderCycle( ControlCycle cycle ) {
		super();
		this.cycle = cycle == null
			? this
			: cycle;
	}

	@Override
	public <T> void instruct( Data<T> data, RenderInstructor<? super T> content,
			RecoveryStrategy strategy ) {
		unsupported( "Wrap with " + RecoverRenderCycle.class.getSimpleName() );
	}

	@Override
	public <T> void guard( Data<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then ) {
		upon( data, condition, then, BaseMarkup.EMPTY );
	}

	@Override
	public <T> void upon( Data<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then, RenderInstructor<? super T> elSe ) {
		if ( condition.fulfilledBy( data ) ) {
			cycle.instruct( data, then );
		} else {
			cycle.instruct( data, elSe );
		}
	}

	@Override
	public <T> void instruct( Data<T> data, RenderInstructor<? super T> content ) {
		content.instructRendering( data, cycle );
	}

	@Override
	public <T, V> V read( Data<? extends T> data, ValuePath<? super T, ? extends V> path ) {
		return data.value( path );
	}

	@Override
	public <T> void render( Data<T> data, Markup<? super T> content ) {
		content.render( data, this );
	}

	@Override
	public <T> void repeat( Iterable<Data<T>> data, RenderInstructor<? super T> content ) {
		counters.addFirst( 1 );
		for ( Data<T> elemData : data ) {
			//FIXME provide counter also in data
			cycle.instruct( elemData, content );
			counters.set( 0, counters.get( 0 ) );
		}
		counters.removeFirst();
	}

	@Override
	public MarkupStream stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T util( UtilFactory<T> factory ) {
		throw new UnsupportedOperationException( "Not yet implemented!" );
	}

	private void unsupported( String message ) {
		throw new UnsupportedOperationException( message );
	}

	@Override
	public int counter( int depth ) {
		return depth >= counters.size()
			? 0
			: counters.get( depth );
	}

}
