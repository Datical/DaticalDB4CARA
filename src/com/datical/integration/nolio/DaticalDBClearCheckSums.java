package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Clear Check Sums", 
		description = "This action clears ChangeSet Check Sums located in Database ChangeLog Table.", 
		category = "Datical.Clear Check Sums")

public class DaticalDBClearCheckSums extends DaticalDBActionWithTarget implements NolioAction {

	private static final long serialVersionUID = -853898390122210109L;

	public DaticalDBClearCheckSums() {
		super();
		setDaticalDBAction("clearCheckSums");
	}
}