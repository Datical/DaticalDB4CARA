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
		name = "Datical DB Set Project Parameters",
		description = "This will create and set parameters for Datical DB execution. Useful for when you only have a Datical DB changelog.xml file or simply do not want to create a datical.project file using the GUI.",
		category="Datical")

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
//	•	port 				(REQUIRED)		done
//	•	username 			(REQUIRED)		done
//	•	password 			(REQUIRED)		done
//	•	contexts 			(OPTIONAL)		done
//	•	defaultSchemaName 	(OPTIONAL)		done
//	•	defaultCatalogName	(OPTIONAL)		done
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
			order = 3
			)    
	private DaticalDBVendor daticalDBVendor = DaticalDBVendor.Oracle;

	@ParameterDescriptor(
			// name
			name = "Datical DB Step Name",
			description = "This is an Internal user friendly name for your database.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "MyDB",
			order = 4
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
			order = 5
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
			order = 6
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
			order = 7
			)    
	private String daticalDBUsername = "scott";

	@ParameterDescriptor(
			// port
			name = "Datical DB Database Password",
			description = "The password of the Database Server.",
			out = false,
			in = true,
			nullable = false,
			defaultValueAsString = "tiger",
			order = 8
			)    
	private Password daticalDBPassword = new Password("tiger");

	// optional

	@ParameterDescriptor(
			// context
			name = "Datical DB Context",
			description = "The Context used by Datical DB. Optional.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 9
			)    
	private String daticalDBContext = "";

	@ParameterDescriptor(
			// defaultSchemaName
			name = "Datical DB Default Schema Name",
			description = "The Default Schema Name used by Datical DB. Optional.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 10
			)    
	private String daticalDBDefaultSchemaName = "";
	
	@ParameterDescriptor(
			// defaultCatalogName
			name = "Datical DB Default Catalog Name",
			description = "The Default Catalog Name used by Datical DB. Optional.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 11
			)    
	private String daticalDBDefaultCatalogName = "";
	
	// platform specific

	// DB2, MySQL, PostgreSQL
	
	@ParameterDescriptor(
			// database
			name = "Datical DB Database Name (DB2, MSSQL, MySQL, PostgreSQL)",
			description = "The Database Name. Required for DB2, MySQL, PostgreSQL.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 12
			)    
	private String daticalDBDatabaseName = "";
	
	// Oracle
	@ParameterDescriptor(
			// sid
			name = "Datical DB SID (Oracle)",
			description = "The Database SID. Either SID or Service Name is required for Oracle.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 13
			)    
	private String daticalDBSID = "";
	
	@ParameterDescriptor(
			// serviceName
			name = "Datical DB Service Name (Oracle)",
			description = "The Database Service Name. Either SID or Service Name is required for Oracle.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 14
			)    
	private String daticalDBServiceName = "";
	
	
	// MSSQL

	@ParameterDescriptor(
			// isIntegratedSecurity
			name = "Integrated Security? (MSSQL)",
			description = "Determines if the database uses Integrated Security. Optional for MSSQL.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 15
			)  
	private DaticalDBBinary daticalDBIsIntegratedSecurity = null;
	
	@ParameterDescriptor(
			// applicationName
			name = "Datical DB Application Name (MSSQL)",
			description = "The Datical DB Application Name. Optional for MSSQL.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 16
			)    
	private String daticalDBApplicationName = "";
	
	@ParameterDescriptor(
			// instanceName
			name = "Datical DB Instance Name (MSSQL)",
			description = "The Datical DB Instance Name. Optional for MSSQL.",
			out = false,
			in = true,
			nullable = true,
			defaultValueAsString = "",
			order = 17
			)    
	private String daticalDBInstanceName = "";
	
	@Override
	public ActionResult executeAction() {
		
		// input validation
		String daticalDBRef = "";
		if (daticalDBVendor.equals("Oracle") ) {
			daticalDBRef = "OracleDbDef";
		} else if (daticalDBVendor.equals("MSSQL")) {
			daticalDBRef = "SqlServerDbDef";
		} else if (daticalDBVendor.equals("MySQL")) {
			daticalDBRef = "MysqlDbDef";
		} else if (daticalDBVendor.equals("PostgreSQL")) {
			daticalDBRef = "PostgresqlDbDef";
		} else if (daticalDBVendor.equals("DB2")){
			daticalDBRef = "Db2DbDef";
		} else {
			return new ActionResult(false, "Invalid value for Datical DB Vendor: " + daticalDBVendor.toString()); 
		}

		if (daticalDBVendor.equals("MySQL") || daticalDBVendor.equals("PostgreSQL") || daticalDBVendor.equals("DB2") || daticalDBVendor.equals("MSSQL")) {
			if (daticalDBDatabaseName.equals("")) {
				return new ActionResult(false, "For Database Vendor " + daticalDBVendor.toString() + ", Datical DB Database Name is required."); 
			}
		}

		if (daticalDBVendor.equals("Oracle")) {
			if (daticalDBSID.equals("") && daticalDBServiceName.equals("")) {
				return new ActionResult(false, "For Database Vendor " + daticalDBVendor.toString() + ", SID or Service Name is required.");
			}
		}

		// build List command
		List<String> command = new ArrayList();

		command.add(daticalDBLocation);
		command.add("--project");
		command.add(daticalDBProjectDirectory); 
		command.add("newDbDef"); 
		command.add("DbDefClass"); 
		command.add(daticalDBRef); 
		command.add("name"); 
		command.add(daticalDBStepName); 
		command.add("username"); 
		command.add(daticalDBUsername); 
		command.add("password"); 
		command.add(daticalDBPassword.toString()); 
		command.add("hostname"); 
		command.add(daticalDBHost); 
		command.add("port"); 
		command.add(daticalDBPort);

		if (setParameter(daticalDBDatabaseName)) {
			if (daticalDBVendor.equals("MSSQL")) {
				command.add("databaseName");
			} else {
				command.add("database");
			}
			command.add(daticalDBDatabaseName);
		}

		if (setParameter(daticalDBSID)) {
			command.add("sid");
			command.add(daticalDBSID);
		}

		if (daticalDBIsIntegratedSecurity != null) {
			if (setParameter(daticalDBIsIntegratedSecurity.toString())) {
				command.add("isIntegratedSecurity");
				command.add(daticalDBIsIntegratedSecurity.toString().toLowerCase());
			}
		}

		if (setParameter(daticalDBApplicationName)) {
			command.add("applicationname");
			command.add(daticalDBApplicationName);
		}

		if (setParameter(daticalDBContext)) {
			command.add("context");
			command.add(daticalDBContext);
		}

		if (setParameter(daticalDBDefaultCatalogName)) {
			command.add("defaultCatalogName");
			command.add(daticalDBDefaultCatalogName);
		}

		if (setParameter(daticalDBDefaultSchemaName)) {
			command.add("defaultSchemaName");
			command.add(daticalDBDefaultSchemaName);
		}

		if (setParameter(daticalDBInstanceName)) {
			command.add("instancename");
			command.add(daticalDBInstanceName);
		}

		if (setParameter(daticalDBServiceName)) {
			command.add("serviceName");
			command.add(daticalDBServiceName);
		}

		// execution
		String daticalDBOutput = "";
		try {
			_log.info("Starting Datical DB.");
			Process p = new ProcessBuilder(daticalDBLocation, "--project", daticalDBProjectDirectory, "newProject").start();			
			_log.info("Waiting for Datical DB to complete.");
			p.waitFor();
			_log.info("Datical DB completed.");

			_log.info("Starting Datical DB.");
			Process p2 = new ProcessBuilder(command).start();			
			_log.info("Waiting for Datical DB to complete.");
			p2.waitFor();
			_log.info("Datical DB completed.");




			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;
			while ((line = b.readLine()) != null) {
				daticalDBOutput = daticalDBOutput + "\n" + line;
			}
			
			b = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			while ((line = b.readLine()) != null) {
				daticalDBOutput = daticalDBOutput + "\n" + line;
			}

		} catch (IOException | InterruptedException e) {
			_log.error(e.toString());
		}

		_log.info(daticalDBOutput);

		return new ActionResult(true, daticalDBOutput);

	}

	private boolean setParameter(String parameter) {
		
		if (parameter.isEmpty() || parameter.equals("")) {
			return false; 
		} 
		return true;
		
	}
}
