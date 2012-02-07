package de.jbee.earthworm.module;

import java.util.ArrayList;
import java.util.List;

import de.jbee.data.Data;
import de.jbee.data.DataProperty.ObjectProperty;
import de.jbee.data.DataProperty.ValueProperty;
import de.jbee.earthworm.process.ControlCycle;

public final class BasePreparationCycle<T>
		implements PreparationCycle<T> {

	// es wird hier ja noch einen weg (methode) geben, um den prepare vorgang abzuschließen
	// und die sequenz von IMarkup als page festzuhalten - dort können die proxy's für die container rausgestript werden

	private final ContainerMarkup<T> container = new ContainerMarkup<T>();

	@Override
	public void constant( CharSequence markup ) {
		append( BaseMarkup.constant( markup ) );
	}

	@Override
	public void variable( ValueProperty<? super T, ? extends CharSequence> path ) {
		append( BaseMarkup.dynamic( path ) );
	}

	@Override
	public <E> PreparationCycle<E> in( Container<? super T, E> container ) {
		BasePreparationCycle<E> containerCycle = new BasePreparationCycle<E>();
		append( container.encapsulate( containerCycle.container ) );
		return containerCycle;
	}

	@Override
	public <V> void prepare( ObjectProperty<? super T, V> path, Component<V> component ) {
		BasePreparationCycle<V> componentCycle = new BasePreparationCycle<V>();
		append( BaseMarkup.partial( path, componentCycle.container ) );
		component.prepare( componentCycle );
	}

	private <V> boolean append( RenderInstructor<? super T> element ) {
		return container.elements.add( element );
	}

	static final class ContainerMarkup<T>
			implements RenderInstructor<T> {

		final List<RenderInstructor<? super T>> elements = new ArrayList<RenderInstructor<? super T>>();

		@Override
		public void instructRendering( Data<? extends T> data, ControlCycle cycle ) {
			for ( RenderInstructor<? super T> e : elements ) {
				cycle.instruct( data, e );
			}
		}

	}
}
