package de.jbee.earthworm.v2.activity;

public interface Activity {

	// ... ist die Transition oder der Arbeitsvorgang/Aurbeitsauftrag, welche durch ein Get/Post-Request verarbeitet wird (oder nicht)
	
	// Klassifizierung(en)
	// Sind alle Parameter serverseitig - also 100% sicher oder kommen noch "Eingaben" vom Client ?
	
	// Eigenschaften:
	// - Erzeugungszeitpunkt (wichtig für eine Disposerweite TTL aller Aktivitäten die grundsätzlich auslaufen können)
	
}
