package de.jbee.earthworm.v2.activity;

public interface ActivityPolicy {
	// Für immer gültig
	// verfällt sobald der selbe nutzer diese oder eine andere aktion ausführt 
	// ...
	
	// es gibt sicherlich mehr sinnvolle möglichkeiten aktivitäten zu verwalten als man hier zunächst vorsieht
	// aber wie soll man sonst die verarbeitung abbilden, die ja stark in disposer steckt ?
	// ein zweites problem: wahrscheinlich ist es sinnvoll einige policys kombinieren zu können
	// also.. was kann man da machen ? --> Ein Set pro aktivität ? wäre schonmal kombinierbar
	// die policy könnte auch den disposer und die aktivität als argument erhalten mit der aufforderung ihre folgen auszufühen,
	// dann wäre das kein enum sondern jeweils dafür passende klassen und das ganze wird individuell anpassbar
	// die policys selbst sollten hinsichtlich der aktivitäten zustandslos sein, damit man hier singletons nutzen kann

	// es muss auch bedacht werden, dass aktivitäten auslaufen - dies ist allgemein von einem ereignis abhängig.
	// Ein Ereignis kann etwa sein: TTL abgelaufen, andere aktivität ausgeführt (selber Nutzer oder anderer auf selben daten)
	// !!!! AUCHTUNG !!! Aktivitäten sind nur eine sache des client interfaces - hier irgendwelche geschäftslogiken
	// zu involvieren führt zu komplexem nicht mehr einfach vorhersehbaren Verhalten --> MIST
	// Aktivitäten sollten also nur von Client-Interface spezifischen Aktivitäten beeinflusst werden.
	// Klassiker: Andere Aktivität wurde ausgeführt, Seite XY betrachtet etc. 
	
	/**
	 * Wendet diese Richtlinie auf activity an, indem die dafür nötigen Schritte
	 * über den disposer ausgeführt werden
	 * 
	 * hier ist es wichtig, dass die policy weiß, ob die aktivität ausgeführt
	 * wurde, oder eine andere des selben clients - denn es müssen immer alle
	 * eine clients aktualisiert werden -> 2. Methode - meist will man nämlich
	 * für die beiden fälle eh unterschiedliche dinge tun und so würde es
	 * lesbarer und weniger if lastig als mit einem parameter
	 */
	void exertOn(Activity choosen, ActivityDisposer disposer);
	// exertOnChoosen und extertOnSkipped oder sowas 
	
	void exertOn(Activity choosen, Activity omitted, ActivityDisposer disposer);
	
	// Wer hat die Referenz ? Aktivität auf ihre Policys oder Teil des Disposers oder Policys haben Liste der Aktivitäten ?
	// Was bietet sich an ? Warum ? 
	
	// Was will man konkret für Policys ?
	// - Löschen bei Ausführung dieser (oder einer anderen Aktivität)
	// - Löschen durch Logout/TTL (Client arbeitet nicht länger)
	// - Löschen/Ersetzen/Wiederverwenden bei erneutem Anzeigen der beinhaltenden Seite (für Links interessant - überhaupt ist interessant, ob neben der Serverseitigen params noch welche vom client kommen)
	//   Hier sind wohl Gruppierung von Aktivitäten interessant (pro Page, pro Aktion, die dahinter steht (wenn eindeutig))
	
	// Grundsätzlich ist es kaum sinnvoll Aktivitäten zu lange oder zu intelliegent verwalten zu wollen, da einzig der Client 
	// den Gebrauch kennt. Daher sich die Frage, wie man möglichst mit wenigen einfachen Regeln vermeintlich nicht verwendete Aktivitäten erkennt und entfernt/aufräumt.
	// Das führt zu weiteren Fragen:
	// - Wann benutzt der Client Tabs (neue Fenster), sodass die zuletzt angezeigten Links weiter verwedent werden könnten und somit aus der Erwartung des Clients 
	//   noch gültig sein sollten ???? ---> TTL ???? ---Kann man evtl. etwas über den Referer ermitteln ? 
	
	// D I E   L Ö S U N G  .. liegt irgendwo in einer gescheiten Organisation der Aktivitäten - einer Art Historie 
	// ByTheWay: Die Aufgerufenen Aktivitäten sollten (mit vollständigen Parmetern (sofern gültig) in eine art Historie geschoben werden (kann man abschlatbar machen)  
	
	// Der Lebenszyklus oder vielmehr sein Ende ist der springende Punkt:
	// - Anzeigende Seiten (Listen, Tabellen, Übersichten etc.) sind klassisch für den Client zugeschnitten und somit für die Dauer der Session gültig
	//   Wiederholte Aufrufe arbeiten ohnehin auf aktuellen Daten (neues Rendern)

	// Zugriff: READ / WRITE
	// Gültigkeit: [alle enthalten impliziert EXPIRE_USE im Verhalten]
	// - EXPIRE_ALWAYS (jede Aktivität (des Clients) wirft auch diese raus READ oder WRITE)
	// - EXPIRE_USE (wenn diese! Aktivität verwendet wird - One-Time-Call) 
	// - EXPIRE_WRITE (bei jeder Schreibenden Aktivität)
	// - EXPIRE_HEAP (fällt aus dem Puffer wenn es dran ist)
	// - EXPIRE_NEVER (fällt nie aus dem Puffer - nur für sehr allgemeine Funktionen - muss überwacht werden, zuviele darf es davon nicht geben)
	// - EXPIRE_CUSTOM (hier wird dann eine eigene policy mit angegeben -- nur spärlich gebrauchen!)
	// Gruppe: Das ganze noch im Zusammenspiel der Gruppe +++ (da sollte man sicherlich einfach eine 2. einstellung "Wenn in Gruppe" haben --> Konstanten können wiederverwendet werden)

	// Über diese generelle Gültigkeit hinnaus möchte man zudem soviele Leichen wie möglich wegräumen, weil die Seite beim Client ohnehin nicht mehr sichtbar ist.
	// Dazu muss der Client aktiv darüber informieren, welche
	// *=Birth, >=Runs, 0=Displayed, +=Exists (abhängig von Gültigkeit), x=Kann gelöscht werden
	// 		T	F	A1 	A2	A3	A4
	// S	0	1 	>	*	*	*
	// C	1	1/2	+	0	0	0
	// S	2a	1	x	>	x	x
	// S	2b	2	+	>	+	+
	
	// S	n	cA2	
	
}
