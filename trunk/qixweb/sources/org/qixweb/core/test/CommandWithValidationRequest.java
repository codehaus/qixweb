package org.qixweb.core.test;

import org.qixweb.core.*;


public class CommandWithValidationRequest extends WebCommandRequest
{
    private static Browsable itsDestinationWhenNotValid = null;
    private static boolean itsValidationFlag;

    public CommandWithValidationRequest(Parameters submittedValues)
    {
        super(submittedValues);
    }

    public boolean isValid()
    {
        return itsValidationFlag;
    }

    public Browsable destinationWhenNotValid(QixwebEnvironment environment)
    {
        return itsDestinationWhenNotValid;
    }
    
    public static void programNotValidWithDestination(Browsable destination)
    {
        itsValidationFlag = false;
        itsDestinationWhenNotValid = destination;
    }

    public static void programValidRequestWithNotValidDestination(Browsable destination)
    {
        itsValidationFlag = true;
        itsDestinationWhenNotValid = destination;
    }
	
}
