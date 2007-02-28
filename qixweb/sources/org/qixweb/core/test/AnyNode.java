package org.qixweb.core.test;

import java.util.HashMap;

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

    public AnyNode(String aTitle, HashMap errorMessages)
    {
        itsTitle = aTitle;
        setErrorMessages(errorMessages);
    }

    public String title()
    {
        return itsTitle;
    }

    public QixwebUrl anyCommandLink()
    {
        return new QixwebUrl(AnyCommand.class);
    }

    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
    {
        return new AnyNode();
    }

    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new AnyNode();
    }

    public boolean equals(Object obj)
    {
        return DeepEquals.equals(this, obj);
    }
}
