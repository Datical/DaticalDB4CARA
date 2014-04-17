package com.datical.integration.nolio;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.NolioAction;

@ActionDescriptor(
		name = "Datical DB Forecast", 
		description = "This action forecasts changes in the ChangeLog file to the Target Database.", 
		category = "Datical")

public class DaticalDBForecast extends DaticalDBActionWithTargetAndReportOutput implements NolioAction{
	
	private static final long serialVersionUID = 8990231613237623473L;

	public DaticalDBForecast() {
		super();
		setDaticalDBAction("forecast");
	}

}
