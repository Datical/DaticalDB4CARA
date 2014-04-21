package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Create New Project", 
		description = "This will create a new Datical DB project. Useful (in conjunction with crating a new DB Definition) for when you only have the changelog.xml file for a Datical DB deployment.",
		category = "Datical")

public class DaticalDBCreateDaticalProject extends DaticalDBActionWithoutTarget implements NolioAction {

	private static final long serialVersionUID = 380201392224352221L;

	public DaticalDBCreateDaticalProject() {
		super();
		setDaticalDBAction("newProject");
	}
}

