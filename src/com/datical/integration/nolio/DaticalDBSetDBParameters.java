package com.datical.integration.nolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nolio.platform.shared.api.*;

@ActionDescriptor(
		name = "Hello Action",
		description = "This action receives a name and returns a welcome greeting.",
		category="Greeting.Hello")

public class DaticalDBSetDBParameters implements NolioAction {
	
	private static final long serialVersionUID = 3914890651882009063L;


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
	
	
	
//	Base properties	 
//	•	name 				(REQUIRED) 		done
//	•	driver 				(AUTOMATIC)		N/A
//	•	hostname 			(REQUIRED)		done
//	•	port 				(REQUIRED)
//	•	username (REQUIRED)
//	•	password (REQUIRED)
//	•	contexts (OPTIONAL)
//	•	defaultSchemaName (OPTIONAL)
//	•	defaultCatalogName (OPTIONAL)
//	MySQLDbDef
//		database (REQUIRED)
//	PostgresDbDef
//		database (REQUIRED)
//	Db2DbDef
//	•	database (REQUIRED)
//	OracleDbDef
//	•	sid (REQUIRED)
//	•	serviceName (OPTIONAL)
//	SqlServerDbDef
//	•	databaseName (REQUIRED)
//	•	isIntegratedSecurity (OPTIONAL)
//	•	applicationName (OPTIONAL)
//	•	instanceName (OPTIONAL)


	//hammer newDbDef DbDefClass OracleDbDef name target1 database petshop sid orcl username r2 password shredder hostname proddb1.datical.net
	

	@ParameterDescriptor(
			// TODO: Convert to ENUM
//			MysqlDbDef
//			SqlServerDbDef
//			OracleDbDef
//			Db2DbDef

			// DbDefClass
			name = "Database Vendor",
			description = "The target database vendor. Valid values are Oracle, DB2, MSSQL, MySQL, PostgreSQL.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "Oracle",
			order = 1
			)    
	private String daticalDBVendor = "oracle";

	@ParameterDescriptor(
			// name
			name = "Datical DB Database Name",
			description = "This is an Internal user friendly name for your database.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "MyDB",
			order = 1
			)    
	private String daticalDBStepName = "MyDB";

	@ParameterDescriptor(
			// hostname
			name = "Datical DB Database Hostname",
			description = "The hostname or IP address of the Database Server.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "192.168.0.1",
			order = 1
			)    
	private String daticalDBHost = "192.168.0.1";

	@ParameterDescriptor(
			// port
			name = "Datical DB Database Port",
			description = "The port of the Database Server.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "1521",
			order = 1
			)    
	private String daticalDBPort = "1521";

	@ParameterDescriptor(
			// port
			name = "Datical DB Database Username",
			description = "The username of the Database Server.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "scott",
			order = 1
			)    
	private String daticalDBUsername = "scott";

	
		

	
	
	@ParameterDescriptor(
			name = "Welcome String",
			description = "Welcome greeting",
			out = true,
			in = false)
	private String welcomeString;

	@Override
	public ActionResult executeAction() {

		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, "newProject").start();			
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
