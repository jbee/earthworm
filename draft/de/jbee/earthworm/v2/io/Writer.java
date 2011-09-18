package de.jbee.earthworm.v2.io;

// könnte man auch mal Speaker nennen - würde zumindest namenskollisionen vorbeugen- wenn auch etwas ungewöhnlich

/*
 * STAteless Remote Control Application Kit
 * 
 * oder doch nochmal StreamCat
 * STateless REmote ?? Contol Application Toolkit
 */

public interface Writer {

	// Erstmal nur ein Patzhalter für die Ausgabe.
	// Grundsätzlich wird der OutputStream eines Servlets oder einer Datei umwickelt
	
	// Wichtig: Für Links und Forms sollten spezialisierte Methoden verfügbar sein:
	// So kann man über einen dafür spezieliserten Writer lediglich alle möglichen Aktionen "ausgeben"
	// Für Forms hieße dies auch, information für die variablen Felder anzibieten, sodass man daraus auch
	// automatisch passende und unpassende Testdaten erzeugen und gegenschmeißen kann.
	// Testen wird eine echt freude via 100% plain unit test :) YEEHAAA
}
