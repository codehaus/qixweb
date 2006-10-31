package org.qixweb.core.test;

import org.qixweb.core.*;


public class NotExecutableCommand extends WebCommand
{
    public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
    {
        return new NotExecutableCommand();
    }

    public Browsable execute(QixwebEnvironment system)
    {
        return null;
    }
    
    public boolean canBeExecutedBy(QixwebUser aUser, QixwebEnvironment environment)
    {
        return false;
    }
}
