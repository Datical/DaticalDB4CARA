package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Snapshot", 
		description = "This action creates a Snapshot of the Target Database.", 
		category = "Datical")

public class DaticalDBSnapshot extends DaticalDBActionWithTarget implements NolioAction{

	private static final long serialVersionUID = 1L;

	public DaticalDBSnapshot() {
		super();
		setDaticalDBAction("snapshot");
	}
}