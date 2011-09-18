package de.jbee.earthworm.v2.activity;

public interface ActivityGroup {

	// Eine Art rudimentäre Nutzerverwaltung:
	// Verschiedene Benutzer werden zu eiener Gruppe zusammengefasst, um Aktiviäten im Kontext der Gruppe zu verwalten
	// und Grundlegende Locks zu ermöglichen
	// Gruppen sind etwas dynamisches - Sie haben nichts mit klassischen Nutzergruppen einer Rechteverwaltung gemein
	// Ein Client kann etwa zwischen Gruppen wechseln, je nachdem wo auf einer Seite er sich bewegt --> Denn das bestimmt auch evtl. Kollisionen
	// Sollte er auch in mehreren Gruppen sein können ? Klingt erstmal kompliziert und unvernünftig
	// --> Wenn eine Gruppe (Wann ist man darin) gut definiert ist, sollte ein Client (der gewöhnlich auch nur 1 Ziel zur Zeit hat), auch nur
	// in einer Gruppe sein müssen
	
	// Umsetzung:
	// Wahrscheinlich doch leichter als gedacht - Eine Gruppe bedeutet letztlich wohl nur, dass Aktivitäten zur Aktualisierung aller Heaps der Mitglieder führt
	
	// Gruppeneigenschaften:
	// - Sichtbarkeit: Public/Protected/Privat: Heißt weiß der Nutzer, dass er darin ist, und wer die anderen sind 
	//		Public: Er erhält etwa eine Anzeige wie: Nutzer XYZ tut auch grad dies! --> typisch CMS --- oder auch: Sie sind nun Mitglied der Gruppe XYZ
	//		Protected: Er erhält Informationen nur unter bestimmten Bedingungen, wie einem Auth-Level (Admin-Modus oder sowas) 
	//		Privat: Er weiß weder, dass er in einer Gruppe ist, noch wer ihre Mitgleider sind, was diese tun, oder welche konsequenzen die Mitgliedschaft hat
	
	
	// Eine geile Sache, die über eine dafür gebaute Gruppe möglich sein sollte: Coopoerative Clients mit einem Aktiven Client
	// -> Fern-Hilfestellung (einklingen eines Supervisiors in einen Laufenden Client-Vorgang)
	// -> Administrierung etc.
	
	// Für jede Aktion muss klar sein, über welche Gruppe sie reinkommt
}
