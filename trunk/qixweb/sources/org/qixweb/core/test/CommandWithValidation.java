package org.qixweb.core.test;

import org.qixweb.core.*;

public class CommandWithValidation extends WebCommand
{
    private static QixwebUrl itsExpectedDestination;

    public Browsable execute(QixwebEnvironment notUsed)
    {
        return itsExpectedDestination;
    }

    public static void simulateExecuteReturning(QixwebUrl expectedDestination)
    {
        itsExpectedDestination = expectedDestination;
    }
}
