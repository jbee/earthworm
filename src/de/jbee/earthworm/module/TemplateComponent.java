package de.jbee.earthworm.module;

public interface TemplateComponent<T>
		extends Component<T> {

	// attribute von platzhalter-tags werden auf das tatsächlich gerenderte tag übertragen
	// <ew:component ew:id="foo" class="bar" />

	void prepareSubstitutional( String markup, PreparationCycle<? extends T> cycle );
}
