package de.jbee.earthworm.process;

public class RenderException
		extends RuntimeException {

	private boolean resolved = false;
	private final Object[] hierarchy;

	public RenderException( Throwable cause, Object[] hierarchy ) {
		super( cause );
		this.hierarchy = hierarchy;
	}

	public Object[] getHierarchy() {
		return hierarchy;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void markResolved() {
		resolved = true;
	}
}
