package de.jbee.earthworm.module;

import de.jbee.data.Dataset.ValueProperty;

public class Label<T>
		implements Component<T> {

	private final ValueProperty<T, ? extends CharSequence> valuePath;

	public Label( ValueProperty<T, ? extends CharSequence> valuePath ) {
		super();
		this.valuePath = valuePath;
	}

	@Override
	public void prepare( PreparationCycle<? extends T> cycle ) {
		cycle.variable( valuePath );
	}

}
