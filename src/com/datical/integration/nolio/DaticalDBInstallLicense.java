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
		name = "Datical DB Install License",
		description = "This will install the Datical DB License file.",
		category="Datical")

public class DaticalDBInstallLicense implements NolioAction {
	
	private static final long serialVersionUID = 3914890651882009063L;

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

			name = "Datical DB License File",
			description = "Fully qualified path to the Datical DB License file.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "C:\\Users\\robert\\datical\\myLicense.lic",
			order = 3
			)    
	private String daticalDBLicenseFile = "C:\\Users\\robert\\datical\\myLicense.lic";

	
	@Override
	public ActionResult executeAction() {
		
		// build List command
		List<String> command = new ArrayList();

		command.add(daticalDBLocation);
		command.add("--project");
		command.add(daticalDBProjectDirectory); 
		command.add("installLicense"); 
		command.add(daticalDBLicenseFile); 

		// execution
		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(command).start();			
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

}
