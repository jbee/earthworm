package de.jbee.earthworm.module;

import java.util.ArrayList;
import java.util.List;

import de.jbee.earthworm.data.IData;
import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IValuePath;
import de.jbee.earthworm.process.IControlCycle;

public final class PreparationCycle<T>
		implements IPreparationCycle<T> {

	// es wird hier ja noch einen weg (methode) geben, um den prepare vorgang abzuschließen
	// und die sequenz von IMarkup als page festzuhalten - dort können die proxy's für die container rausgestript werden

	private final ContainerMarkup<T> container = new ContainerMarkup<T>();

	@Override
	public void constant( CharSequence markup ) {
		append( Markup.constant( markup ) );
	}

	@Override
	public void dynamic( IValuePath<? super T, ? extends CharSequence> path ) {
		append( Markup.dynamic( path ) );
	}

	@Override
	public <E> IPreparationCycle<E> in( IContainer<? super T, E> container ) {
		PreparationCycle<E> containerCycle = new PreparationCycle<E>();
		append( container.encapsulate( containerCycle.container ) );
		return containerCycle;
	}

	@Override
	public <V> void prepare( IDataPath<? super T, V> path, IComponent<V> component ) {
		PreparationCycle<V> componentCycle = new PreparationCycle<V>();
		append( Markup.partial( path, componentCycle.container ) );
		component.prepare( componentCycle );
	}

	private <V> boolean append( IRenderInstructor<? super T> element ) {
		return container.elements.add( element );
	}

	static final class ContainerMarkup<T>
			implements IRenderInstructor<T> {

		final List<IRenderInstructor<? super T>> elements = new ArrayList<IRenderInstructor<? super T>>();

		@Override
		public void instructRendering( IData<? extends T> data, IControlCycle cycle ) {
			for ( IRenderInstructor<? super T> e : elements ) {
				cycle.instruct( data, e );
			}
		}

	}
}
