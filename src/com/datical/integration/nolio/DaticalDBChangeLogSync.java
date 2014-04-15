package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB ChangeLog Sync", 
		description = "This action asserts that all ChangeSets located in the ChangeLog have been applied to the Database", 
		category = "Datical")

public class DaticalDBChangeLogSync extends DaticalDBActionWithTarget implements NolioAction {

	private static final long serialVersionUID = -853898390122210109L;

	public DaticalDBChangeLogSync() {
		super();
		setDaticalDBAction("changelogSync");
	}
}