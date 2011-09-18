package de.jbee.earthworm.module;

public interface ITemplateComponent<T>
		extends IComponent<T> {

	// attribute von platzhalter-tags werden auf das tatsächlich gerenderte tag übertragen
	// <ew:component ew:id="foo" class="bar" />

	void prepareSubstitutional( String markup, IPreparationCycle<? extends T> cycle );
}
