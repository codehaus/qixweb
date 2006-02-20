package org.qixweb.core.test;

import org.qixweb.core.*;


public class NotAuthorizedNode extends WebNode
{
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
	{
		return new NotAuthorizedNode();
	}
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new NotAuthorizedNode();
    }
    
    protected boolean canBeDisplayedBy(QixwebUser aUser)
    {
        return false;
    }
	
    public boolean equals(Object obj)
	{
		return obj instanceof NotAuthorizedNode;
	}
}
