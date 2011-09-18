package de.jbee.earthworm.v2.activity;

import de.jbee.earthworm.v2.client.Client;
import de.jbee.earthworm.v2.client.ClientId;

public interface ActivityDisposer {

	// verwaltet die arbeitsaufträge 
	
	// warum als interface: es ist praktisch, hier zum testen etc auch einen disposer zu haben, der prüfungen ausläst usw.
	
	void connect(Client client);
	
	void disconnect(ClientId clientId);
}
