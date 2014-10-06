package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Check Rules", 
		description = "This action validates Rules located within the Datical DB Project.", 
		category = "Datical")

public class DaticalDBCheckRules extends DaticalDBActionWithoutTarget implements NolioAction {
	
	private static final long serialVersionUID = -2353494281884924346L;
		
	public DaticalDBCheckRules() {
		super();
		setDaticalDBAction("checkRules");
	}
}
