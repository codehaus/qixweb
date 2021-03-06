package org.qixweb.core.test.support;

import org.qixweb.core.QixwebUrl;
import org.qixweb.core.UserData;
import org.qixweb.core.test.UserDataCreator;

public class WebCommandComparator
{
    public static boolean areEquals(Object expectedCommand, QixwebUrl urlToActualCommand, UserData userDataForActualCommand)
    {
        Object actualCommand = urlToActualCommand.materializeTargetCommandWith(userDataForActualCommand);
        return expectedCommand.equals(actualCommand);
    }

    public static boolean areEquals(Object expectedCommand, QixwebUrl urlToActualCommand)
    {
        return areEquals(expectedCommand, urlToActualCommand, UserDataCreator.EMPTY);
    }
}
