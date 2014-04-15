package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Status Details", 
		description = "This action displays the Datical DB Status Details of a database server.", 
		category = "Datical")

public class DaticalDBStatusDetails extends DaticalDBActionWithTarget implements NolioAction{

	private static final long serialVersionUID = 1L;

	public DaticalDBStatusDetails() {
		super();
		setDaticalDBAction("statusDetails");
	}
}