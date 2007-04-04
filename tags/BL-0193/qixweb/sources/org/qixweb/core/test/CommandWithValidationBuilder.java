package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.core.validation.WebCommandBuilder;

public class CommandWithValidationBuilder extends WebCommandBuilder
{
    private static Browsable itsDestinationWhenNotValid = null;
    private static boolean itsValidationFlag;

    public CommandWithValidationBuilder(Parameters submittedValues)
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

    public WebCommand destinationWhenValid(UserData notUsedUserData)
    {
        return new CommandWithValidation();
    }
}
