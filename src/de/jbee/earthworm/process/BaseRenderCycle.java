package de.jbee.earthworm.process;

import java.util.LinkedList;

import de.jbee.data.Dataset;
import de.jbee.data.Dataset.ValueProperty;
import de.jbee.earthworm.module.BaseMarkup;
import de.jbee.earthworm.module.Conditional;
import de.jbee.earthworm.module.Markup;
import de.jbee.earthworm.module.RenderInstructor;
import de.jbee.lang.List;
import de.jbee.lang.Sequence;

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
	public <T> void instruct( Dataset<T> data, RenderInstructor<? super T> content,
			RecoveryStrategy strategy ) {
		unsupported( "Wrap with " + RecoverRenderCycle.class.getSimpleName() );
	}

	@Override
	public <T> void guard( Dataset<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then ) {
		upon( data, condition, then, BaseMarkup.EMPTY );
	}

	@Override
	public <T> void upon( Dataset<T> data, Conditional<? super T> condition,
			RenderInstructor<? super T> then, RenderInstructor<? super T> elSe ) {
		if ( condition.fulfilledBy( data ) ) {
			cycle.instruct( data, then );
		} else {
			cycle.instruct( data, elSe );
		}
	}

	@Override
	public <T> void instruct( Dataset<T> data, RenderInstructor<? super T> content ) {
		content.instructRendering( data, cycle );
	}

	@Override
	public <T, V> V read( Dataset<? extends T> dataset, ValueProperty<? super T, ? extends V> value ) {
		return dataset.value( value );
	}

	@Override
	public <T> void render( Dataset<T> data, Markup<? super T> content ) {
		content.render( data, this );
	}

	@Override
	public <T> void repeat( Sequence<Dataset<T>> items, RenderInstructor<? super T> content ) {
		counters.addFirst( 1 );
		for ( Dataset<T> e : List.iterate.forwards( items ) ) {
			//FIXME provide counter also in data
			cycle.instruct( e, content );
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

}
