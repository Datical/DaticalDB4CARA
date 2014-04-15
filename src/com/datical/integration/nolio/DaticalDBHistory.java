package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB History", 
		description = "This action displays the Datical DB History of a database server.", 
		category = "Datical")

public class DaticalDBHistory extends DaticalDBActionWithTarget implements NolioAction{

	private static final long serialVersionUID = 1L;

	public DaticalDBHistory() {
		super();
		setDaticalDBAction("history");
	}
}