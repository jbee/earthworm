package de.jbee.earthworm.process;

import de.jbee.lang.List;

public class RenderException
		extends RuntimeException {

	private boolean resolved = false;
	private final List<?> hierarchy;

	public RenderException( Throwable cause, List<?> hierarchy ) {
		super( cause );
		this.hierarchy = hierarchy;
	}

	public List<?> getHierarchy() {
		return hierarchy;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void markResolved() {
		resolved = true;
	}
}
