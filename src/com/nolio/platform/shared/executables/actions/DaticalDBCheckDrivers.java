
package com.nolio.platform.shared.executables.actions;

import com.nolio.platform.shared.api.*;
import com.nolio.platform.shared.executables.actions.RunProcessBase;

import com.nolio.platform.shared.utils.OSType;
import com.nolio.platform.shared.utils.OSUtils;


/**
 * An example Nolio action
 * 
 * <p>Date: Apr 10, 2014</p>
 *
 * @author robert
 */
@ActionDescriptor(
    name = "Hello Action",
    description = "This action receives a name and returns a welcome greeting.",
    category="Greeting.Hello" /* Parent category is greeting, and sub category is Hello */)



public class DaticalDBCheckDrivers extends RunProcessBase implements NolioAction  {
    private static final long serialVersionUID = 1L;
    
    @ParameterDescriptor(
    	name = "Username",
        description = "The name of the user",
        out = false,   // Whether parameter is an output.
        in = true,     // Whether parameter is an input.
        nullable = true,   // Whether parameter can have a null value. Must be true if a default value exists.
        defaultValueAsString = "robert", // Used as the default value of this parameter.
        order = 1 // Indicates the order of the parameters in the UI
    )    
    private String username = "robert";
	
    @ParameterDescriptor(
        name = "Welcome String",
        description = "Welcome greeting",
        out = true,
        in = false)
    private String welcomeString;

//    @Override
//    public ActionResult executeAction() {
//    	if ("error".equalsIgnoreCase(username)) {
//    		return new ActionResult(false, "Error occurred. sorry!");
//    	} else {
//			welcomeString = "Hello " + username;
//			return new ActionResult(true, "Hello " + username);
//		}        
//    }

	@Override
	String actionFriendlyName() {
		return "Hello Friendly Name";
	}

	@Override
	protected String[] createCommandsArray() {
		return createCommandsArray(false);
	}

	@Override
	protected String[] createCommandsArray(boolean maskPassword) {
        String command[] = new String[3];
        if(OSUtils.getLocalOSType() == OSType.WINDOWS)
        {
            command[0] = "cmd";
            command[1] = "/c";
        } else
        {
            command[0] = "/bin/sh";
            command[1] = "-c";
        }
        String commandLineToRet = username;
        if(hasMaskedValue("commandLine") && maskPassword)
            commandLineToRet = (String)getMaskedValue("commandLine");
        command[2] = commandLineToRet;
        return command;
	}

	@Override
	protected boolean isRedirectErrorStream() {
		return true;
	}
}
