package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;

public class NotInstantiableNode extends WebNode
{
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
	{
        return null;
	}
    
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return null;
    }
	
    public boolean equals(Object obj)
	{
		return obj instanceof NotInstantiableNode;
	}
}
