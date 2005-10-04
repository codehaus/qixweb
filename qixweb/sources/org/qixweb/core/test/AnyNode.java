package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;

public class AnyNode extends WebNode
{
	private String itsTitle;
    
    public AnyNode()
    {
        this("No Title");
    }
    
    public AnyNode(String aTitle)
    {
        itsTitle = aTitle;
    }
    
    public String title()
    {
        return itsTitle;
    }
    
    public WebAppUrl anyCommandLink()
    {
        return WebAppUrl.createFor(AnyCommand.class);
    }
    
    public static WebNode create(WebAppUrl anUrl, UserData aUserData, TheSystem system)
	{
		return new AnyNode();
	}
    public static WebNode create(WebAppUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new AnyNode();
    }
	
    public boolean equals(Object obj)
	{
		return DeepEquals.equals(this,obj);
	}
}
