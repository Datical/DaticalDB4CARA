package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Status", 
		description = "This action displays the Datical DB Status of a database server.", 
		category = "Datical")

public class DaticalDBStatus extends DaticalDBActionWithTarget implements NolioAction{

	private static final long serialVersionUID = 1L;

	public DaticalDBStatus() {
		super();
		setDaticalDBAction("status");
	}
}