package de.jbee.earthworm;

public class Todo {

	// Find a way:
	// - make the worm go to looping components

	// - to allow simple attribute attachment to parental tags (class/style - CSS)
	// 	-> idee: 	parents können sagen, wo angaben von kindern wie "parent(Attr.CLASS, "bla") landen
	// 	-> 			es gibt dazu auch filter um solche zuordnungen der parents einzuschränken
	//				parentsTarget(Attr.CLASS, aComponentInstance); // etwa ein filter nach attr.
	//	-> idee:	Der ganze bypass für attribute sollte auf IComponent-Ebene sein und für die 
	//				Markup-Ebene bereits soweit aufgelöst sein, dass die richtigen attr-pfade in der
	//				Sequence an der richtigen stelle hängen, sodass ein fortlaufender stream generiert
	//				werden kann.

	// - counters in repeaters (and other render variables)

	// - mount paths

	// C S S

	/*
	 * So könnte es gehen: im markup schreibt man erweiterte class attribute.
	 * 
	 * etwa so: class="row.even:even row.odd:odd row.first:blue page.edit:highlighted"
	 * 
	 * statt eines names wird ein schlüssel:name angegeben, wenn eine CSS-Klasse variabel sein soll.
	 * Jedem schlüssel kann dann ein resolver zugeorndet werden, welcher anhand des aktuellen datums
	 * berechnet, ob der fall eingetretetn ist.
	 * 
	 * Es sind auch entwerder/oder angaben möglich:
	 * 
	 * class="row.even?even:odd"
	 * 
	 * hier wird der bedingte ausdruck ? true: false ausgewertet.
	 */
}
