package org.qixweb.core.test;

import org.qixweb.core.*;


public class OnlyLoggedUserCommand extends WebCommand
{
    public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
    {
        return new OnlyLoggedUserCommand();
    }

    public Browsable execute(QixwebEnvironment system)
    {
        return new AnyNode();
    }
    
    public boolean canBeExecutedBy(QixwebUser aUser, QixwebEnvironment environment)
    {
        return !QixwebUser.ANONYMOUS.equals(aUser);
    }
}
