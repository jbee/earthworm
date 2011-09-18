package de.jbee.earthworm.module;

public class Tag
		implements ITag {

	private final String name;

	private Tag( String name ) {
		super();
		this.name = name;
	}

	public static ITag of( String name ) {
		return new Tag( name );
	}

	@Override
	public String name() {
		return name;
	}

}
