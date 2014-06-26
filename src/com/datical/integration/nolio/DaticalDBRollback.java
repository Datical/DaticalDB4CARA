package com.datical.integration.nolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.NolioAction;
import com.nolio.platform.shared.api.ParameterDescriptor;


@ActionDescriptor(
		name = "Datical DB Rollback", 
		description = "This action displays the Datical DB History of a database server.", 
		category = "Datical")


public abstract class DaticalDBRollback implements NolioAction { 	

	private static final long serialVersionUID = 8716891777506609101L;

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
			name = "Datical DB Target Database", 
			description = "The Target Database on which you wish to execute Datical DB. This name is contained in your Datical DB Deployment Plan.", 
			out = false, 
			in = true, 
			nullable = false, 
			defaultValueAsString = "MyDatabase", 
			order = 3)
	private String daticalDBDatabase = "MyDatabase";

	@ParameterDescriptor(
			name = "Datical DB Rollback VersionTarget Database", 
			description = "Valid Versions include the following: 'changeid:id=CHANGESETID', 'count:n=COUNT', 'date:yyyy-MM-dd=YYYY-MM-DD', or 'tag:dbtag=DBTAG'.", 
			out = false, 
			in = true, 
			nullable = false, 
			defaultValueAsString = "channgeid:id=null", 
			order = 4)
	private String daticalDBRollbackVersion = "channgeid:id=null";

	@ParameterDescriptor(
			// Export SQL?
			name = "Export SQL",
			description = "If selected will output SQL as part of Datical DB exection.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "false",
			order = 5
			)  
	private Boolean daticalDBExportSQL = false;
	
	@ParameterDescriptor(
			// Export SQL?
			name = "Only Export SQL",
			description = "If selected will only output SQL and NOT perform a Rollback.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "false",
			order = 6
			)  
	private Boolean daticalDBOnlyExportSQL = false;
	
	public ActionResult executeAction() {
		
		String genSQL = "";
		if (daticalDBExportSQL) {
			genSQL = "--genSQL";
		}
		String onlySQL = "";
		if (daticalDBOnlyExportSQL) {
			onlySQL = "--onlySQL";
		}
		

		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, genSQL, onlySQL, "rollback", daticalDBDatabase, daticalDBRollbackVersion).start();			
			_log.info("Waiting for Datical DB to complete.");
			p.waitFor();
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




}
