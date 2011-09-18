package de.jbee.earthworm.v2.tunnel;

import de.jbee.earthworm.v2.core.Cycle;

public class IfElse<T>
		implements Component<T> {

	private final Component<T> ifCase;
	private final Component<T> elseCase;
	private final Tunnel<Boolean> condition;

	public IfElse(Component<T> ifCase, Tunnel<Boolean> condition,
			Component<T> elseCase) {
		super();
		this.ifCase = ifCase;
		this.condition = condition;
		this.elseCase = elseCase;
	}

	@Override
	public void process(T value, Cycle cycle) {
		// TODO Auto-generated method stub

	}

}
