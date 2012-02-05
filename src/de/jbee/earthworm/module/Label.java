package de.jbee.earthworm.module;

import de.jbee.earthworm.data.Path.ValuePath;

public class Label<T>
		implements Component<T> {

	private final ValuePath<T, ? extends CharSequence> valuePath;

	public Label( ValuePath<T, ? extends CharSequence> valuePath ) {
		super();
		this.valuePath = valuePath;
	}

	@Override
	public void prepare( PreparationCycle<? extends T> cycle ) {
		cycle.variable( valuePath );
	}

}
