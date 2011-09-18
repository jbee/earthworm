package de.jbee.earthworm.process;

import java.util.LinkedList;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IValuePath;
import de.jbee.earthworm.module.IConditional;
import de.jbee.earthworm.module.IMarkup;
import de.jbee.earthworm.module.IRenderInstructor;
import de.jbee.earthworm.module.Markup;

public class RenderCycle
		implements IMarkupCycle, IControlCycle {

	private final IControlCycle cycle;

	private final LinkedList<Integer> counters = new LinkedList<Integer>();

	RenderCycle() {
		this( null );
	}

	RenderCycle( IControlCycle cycle ) {
		super();
		this.cycle = cycle == null
			? this
			: cycle;
	}

	@Override
	public <T> void instruct( IData<T> data, IRenderInstructor<? super T> content,
			IRecoveryStrategy strategy ) {
		unsupported( "Wrap with " + RecoverRenderCycle.class.getSimpleName() );
	}

	@Override
	public <T> void guard( IData<T> data, IConditional<? super T> condition,
			IRenderInstructor<? super T> then ) {
		upon( data, condition, then, Markup.EMPTY );
	}

	@Override
	public <T> void upon( IData<T> data, IConditional<? super T> condition,
			IRenderInstructor<? super T> then, IRenderInstructor<? super T> elSe ) {
		if ( condition.fulfilledBy( data ) ) {
			cycle.instruct( data, then );
		} else {
			cycle.instruct( data, elSe );
		}
	}

	@Override
	public <T> void instruct( IData<T> data, IRenderInstructor<? super T> content ) {
		content.instructRendering( data, cycle );
	}

	@Override
	public <T, V> V read( IData<? extends T> data, IValuePath<? super T, ? extends V> path ) {
		return data.value( path );
	}

	@Override
	public <T> void render( IData<T> data, IMarkup<? super T> content ) {
		content.render( data, this );
	}

	@Override
	public <T> void repeat( Iterable<IData<T>> data, IRenderInstructor<? super T> content ) {
		counters.addFirst( 1 );
		for ( IData<T> elemData : data ) {
			//FIXME provide counter also in data
			cycle.instruct( elemData, content );
			counters.set( 0, counters.get( 0 ) );
		}
		counters.removeFirst();
	}

	@Override
	public IMarkupStream stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T util( IUtilFactory<T> factory ) {
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
