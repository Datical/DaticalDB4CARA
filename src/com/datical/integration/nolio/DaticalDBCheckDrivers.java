package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Check Drivers", 
		description = "This action verifies Datical DB can locate the database drivers.", 
		category = "Datical")

public class DaticalDBCheckDrivers extends DaticalDBActionWithoutTarget implements NolioAction {
	
	private static final long serialVersionUID = -2353494281884924346L;
		
	public DaticalDBCheckDrivers() {
		super();
		setDaticalDBAction("checkdrivers");
	}
}
