package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Deploy with Automatic Rollback", 
		description = "This action deploys changes in the ChangeLog file to the Target Database and will Automatically Rollback to the previous state in the event of an error.", 
		category = "Datical")

public class DaticalDBDeployAutoRollback extends DaticalDBActionWithTarget implements NolioAction{

	private static final long serialVersionUID = -1750887975094218994L;

	public DaticalDBDeployAutoRollback() {
		super();
		setDaticalDBAction("deploy-autoRollback");
	}

}