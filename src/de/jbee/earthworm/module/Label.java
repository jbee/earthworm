package de.jbee.earthworm.module;

import de.jbee.earthworm.data.IData.IValuePath;

public class Label<T>
		implements IComponent<T> {

	private final IValuePath<T, ? extends CharSequence> valuePath;

	public Label( IValuePath<T, ? extends CharSequence> valuePath ) {
		super();
		this.valuePath = valuePath;
	}

	@Override
	public void prepare( IPreparationCycle<? extends T> cycle ) {
		cycle.variable( valuePath );
	}

}
