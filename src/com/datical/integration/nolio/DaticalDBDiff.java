package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Diff Databases (Report)", 
		description = "This action creates a report of the differences between two databases.", 
		category = "Datical")

public class DaticalDBDiff extends DaticalDBActionWithTwoTargets implements NolioAction {
	
	private static final long serialVersionUID = -2353494281884924346L;
		
	public DaticalDBDiff() {
		super();
		setDaticalDBAction("diff");
	}
}
