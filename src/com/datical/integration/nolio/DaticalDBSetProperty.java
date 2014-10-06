package com.datical.integration.nolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.datical.integration.nolio.util.DaticalDBBinary;
import com.datical.integration.nolio.util.DaticalDBVendor;
import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.NolioAction;
import com.nolio.platform.shared.api.ParameterDescriptor;
import com.nolio.platform.shared.api.Password;

@ActionDescriptor(
		name = "Datical DB Set Property",
		description = "This will create a key value property in a specified proejct.",
		category="Datical")

public class DaticalDBSetProperty implements NolioAction {
	
	private static final long serialVersionUID = 3236857162174526370L;

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
			name = "Datical DB Property Name",
			description = "The name of the Datical DB Property.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "drivers",
			order = 3
			)    
	private String daticalDBPropertyName = "drivers";

	@ParameterDescriptor(
			name = "Datical DB Property Value",
			description = "The value of the Datical DB Property.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "C:\\dev\\MySQL",
			order = 4
			)    
	private String daticalDBPropertyValue = "C:\\dev\\MySQL";

	
	@Override
	public ActionResult executeAction() {
		
		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, "set", "property", daticalDBPropertyName, daticalDBPropertyValue).start();			
			_log.info("Waiting for Datical DB to complete.");
			Integer returnCode = p.waitFor();
			if (!returnCode.equals(0)) {
				daticalDBOutput = getDaticalDBOutput(p);
				_log.error(daticalDBOutput);
				return new ActionResult(false, daticalDBOutput);
			}
			_log.info("Datical DB completed.");

			daticalDBOutput = getDaticalDBOutput(p);
			
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

	private boolean setParameter(String parameter) {
		
		if (parameter.isEmpty() || parameter.equals("")) {
			return false; 
		} 
		return true;
		
	}
}
