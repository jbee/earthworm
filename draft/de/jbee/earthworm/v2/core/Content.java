package de.jbee.earthworm.v2.core;

/**
 * Inhalt, der dargestellt werden soll - mit einem konkreten Markup verbunden
 * ist, oder dies erzeugt. Letztlich liefert er es bzw. rendert es über den
 * Writer.
 * 
 */
public interface Content
{

	void render(Cycle cycle);

	// irgendwie muss noch eine Methode rein, mit welcher der Inhalt 
	// optimiert in einfachere Inhalte übersetzt werden kann,
	// sodass kein Markup mehr gelesen werden muss.
	// -> Nurnoch statisches Markup + wirklich dynmaische Teile
	// (statischer Content kann immer bis zum nächsten dynamischen zusammengefasst werden)
}
