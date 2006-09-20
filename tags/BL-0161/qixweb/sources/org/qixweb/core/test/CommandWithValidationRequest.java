package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.core.validation.WebCommandRequest;


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
    
    public static void simulateNotValidWithDestination(Browsable destination)
    {
        itsValidationFlag = false;
        itsDestinationWhenNotValid = destination;
    }

    public static void simulateValidRequest()
    {
        itsValidationFlag = true;
    }

    public WebCommand destinationWhenValid(QixwebUrl notUsedUrl, UserData notUsedUserData)
    {
        return new CommandWithValidation();
    }
}
