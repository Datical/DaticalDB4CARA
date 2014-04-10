package com.datical.integration.nolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nolio.platform.shared.api.*;

/**
 * An example Nolio action
 * 
 * <p>
 * Date: Apr 10, 2014
 * </p>
 * 
 * @author robert
 */
@ActionDescriptor(name = "Datical DB Check Drivers Action", description = "This action verified that Datical DB can access the correct database drivers.", category = "Datical.Check Drivers")
public class DaticalDBCheckDrivers implements NolioAction {
	private static final long serialVersionUID = 1L;

	@ParameterDescriptor(
			name = "Datical DB Location", 
			description = "Fully qualified path to Datical DB CLI installation (e.g., C:\\DaticalDB\\repl\\hammer.bat or /usr/local/bin/DaticalDB/repl/hammer)", 
			out = false, 
			in = true, 
			nullable = false,
			defaultValueAsString = "C:\\DaticalDB-1.32.438\\repl\\hammer.bat", 
			order = 1)
	private String daticalDBLocation = "C:\\DaticalDB-1.32.438\\repl\\hammer.bat";

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
        name = "Datical DB Output",
        description = "Output of the Datical DB Command",
        out = true,
        in = false)
    private String daticalDBOutput;	
	
	
	@Override
	public ActionResult executeAction() {

		String output = "";
		try {
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, "checkdrivers").start();
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((output = b.readLine()) != null) {
				daticalDBOutput = daticalDBOutput + "\n" + output;
			}

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ActionResult(true, daticalDBOutput);

	}
}
