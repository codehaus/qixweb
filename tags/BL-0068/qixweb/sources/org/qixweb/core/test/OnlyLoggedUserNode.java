package org.qixweb.core.test;

import org.qixweb.core.*;


public class OnlyLoggedUserNode extends WebNode
{
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
	{
		return new OnlyLoggedUserNode();
	}
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new OnlyLoggedUserNode();
    }
    
    protected boolean canBeDisplayedBy(QixwebUser aUser)
    {
        return !QixwebUser.NULL.equals(aUser);
    }
	
    public boolean equals(Object obj)
	{
		return obj instanceof OnlyLoggedUserNode;
	}
}
