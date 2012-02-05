package de.jbee.earthworm.module;

public class BaseTag
		implements Tag {

	private final String name;

	private BaseTag( String name ) {
		super();
		this.name = name;
	}

	public static Tag of( String name ) {
		return new BaseTag( name );
	}

	@Override
	public String name() {
		return name;
	}

}
