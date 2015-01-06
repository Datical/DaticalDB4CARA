package com.datical.integration.nolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.NolioAction;
import com.nolio.platform.shared.api.ParameterDescriptor;

public abstract class DaticalDBActionWithTwoTargets implements NolioAction { 	

	private String daticalDBAction = null;

	public String getDaticalDBAction() {
		return daticalDBAction;
	}

	public void setDaticalDBAction(String daticalDBAction) {
		this.daticalDBAction = daticalDBAction;
	}

	private static final long serialVersionUID = 1L;

	@ParameterDescriptor(
			name = "Datical DB Location", 
			description = "Fully qualified path to Datical DB CLI installation (e.g., C:\\DaticalDB\\repl\\hammer.bat or /usr/local/bin/DaticalDB/repl/hammer)", 
			out = false, 
			in = true, 
			nullable = false,
			defaultValueAsString = "C:\\Program Files (x86)\\DaticalDB\\repl\\hammer.bat", 
			order = 1)
	private String daticalDBLocation = "C:\\Program Files (x86)\\DaticalDB\\repl\\hammer.bat";

	@ParameterDescriptor(
			name = "Datical DB Project Directory", 
			description = "The Datical DB Project Directory is the path location of your Datical DB Project folder. This will contain a datical.project file.", 
			out = false, 
			in = true, 
			nullable = false, 
			defaultValueAsString = "C:\\Users\\robert\\datical\\BigProject", 
			order = 2)
	private String daticalDBProjectDirectory = "C:\\Users\\robert\\datical\\BigProject";

	@ParameterDescriptor(
			name = "Datical DB Reference Database", 
			description = "The Reference Database on which you wish to execute Datical DB. This name is contained in your Datical DB Deployment Plan.", 
			out = false, 
			in = true, 
			nullable = false, 
			defaultValueAsString = "ReferenceDatabase", 
			order = 3)
	private String daticalDBReferenceDatabase = "ReferenceDatabase";
	

	@ParameterDescriptor(
			name = "Datical DB Target Database", 
			description = "The Target Database on which you wish to execute Datical DB. This name is contained in your Datical DB Deployment Plan.", 
			out = false, 
			in = true, 
			nullable = false, 
			defaultValueAsString = "TargetDatabase", 
			order = 4)
	private String daticalDBTargetDatabase = "TargetDatabase";
	
	@ParameterDescriptor(
			name = "Datical DB Labels", 
			description = "The Labels for the Change Sets to be applied.", 
			out = false, 
			in = true, 
			nullable = true, 
			defaultValueAsString = "$all", 
			order = 5)
	private String daticalDBLabels = "";
	
	
	public ActionResult executeAction() {
		
		String labelArg = "";
		if (daticalDBLabels != null && !daticalDBLabels.isEmpty()) {
			labelArg = "--assignLabels";
		}

		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, labelArg, daticalDBLabels, daticalDBAction, daticalDBReferenceDatabase, daticalDBTargetDatabase).start();			
			_log.info("Waiting for Datical DB to complete.");
			Integer returnCode = p.waitFor();
			if (!returnCode.equals(0)) {
				return new ActionResult(false, getDaticalDBOutput(p));
			}
			_log.info("Datical DB completed.");
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;
			while ((line = b.readLine()) != null) {
				daticalDBOutput = daticalDBOutput + "\n" + line;
			}

		} catch (IOException | InterruptedException e) {
			_log.error(e.toString());
		}

		_log.info(daticalDBOutput);
		return new ActionResult(true, daticalDBOutput);

	}
	
	private String getDaticalDBOutput(Process p) {
		String myOut = "";
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		try {
			while ((line = b.readLine()) != null) {
				myOut = myOut + "\n" + line;
			}
		} catch (IOException e) {
			_log.error(e.toString());
		}
		return myOut;
	}





}
