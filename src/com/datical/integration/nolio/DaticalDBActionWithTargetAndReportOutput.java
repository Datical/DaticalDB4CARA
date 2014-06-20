package com.datical.integration.nolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.datical.integration.nolio.util.DaticalDBBinary;
import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.NolioAction;
import com.nolio.platform.shared.api.ParameterDescriptor;

public abstract class DaticalDBActionWithTargetAndReportOutput implements NolioAction { 	

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
	private String daticalDBTargetDatabase = "MyDatabase";
	
	@ParameterDescriptor(
			// Export SQL?
			name = "Export SQL",
			description = "If selected will output SQL as part of Datical DB exection.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "false",
			order = 4
			)  
	private Boolean daticalDBExportSQL = false;
	
	@ParameterDescriptor(
			// Export SQL?
			name = "Export Rollback SQL",
			description = "If selected will output rollback SQL as part of Datical DB exection.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "false",
			order = 5
			)  
	private Boolean daticalDBExportRollbackSQL = false;
	
	@ParameterDescriptor(
			name = "Datical DB Report Output", 
			description = "Fully qualified Datical DB Report File Output Parameter.", 
			out = true, 
			in = false)
	private String daticalDBReport;
	
	@ParameterDescriptor(
			name = "Datical DB Report Directory Output", 
			description = "Fully qualified path to Datical DB Report Output Parameter.", 
			out = true, 
			in = false)
	private String daticalDBReportDir;
	
	public ActionResult executeAction() {

		String genSQL = "";
		if (daticalDBExportSQL) {
			genSQL = "--genSQL";
		}
		String genRollbackSQL = "";
		if (daticalDBExportRollbackSQL) {
			genRollbackSQL = "--genRollbackSQL";
		}
		
		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, genSQL, genRollbackSQL, daticalDBAction, daticalDBTargetDatabase).start();			
			_log.info("Waiting for Datical DB to complete.");
			Integer returnCode = p.waitFor();
			if (!returnCode.equals(0)) {
				return new ActionResult(false, getDaticalDBOutput(p));
			}
			_log.info("Datical DB completed.");
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;
			while ((line = b.readLine()) != null) {
				
				
				if (line.contains("Report ready at ")) {
					//Report ready at C:\Users\robert\datical\BigProject\Reports\deploy_Dev_20140417_135821\deployReport.html
					String myLine = line;
					myLine = myLine.replace("Report ready at ", "");
					myLine = myLine.replaceAll("(\\r|\\n)", "");
					daticalDBReport = myLine;
					myLine = myLine.replace("deployReport.html", "");
					myLine = myLine.replace("forecastReport.html", "");
					daticalDBReportDir = myLine;
				}
				
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
