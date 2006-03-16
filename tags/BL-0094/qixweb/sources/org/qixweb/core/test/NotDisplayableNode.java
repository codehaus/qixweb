package org.qixweb.core.test;

import java.io.IOException;

import org.qixweb.core.*;


public class NotDisplayableNode extends WebNode
{
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
	{
		return new NotDisplayableNode();
	}
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new NotDisplayableNode();
    }
    
    public boolean equals(Object obj)
	{
		return obj instanceof NotDisplayableNode;
	}
    public void displayThrough(ResponseHandler aResponseHandler) throws IOException
    {
        throw new IOException("Generated Excpetion by NotDisplayableNode");
    }
}
