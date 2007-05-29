package org.qixweb.core;

public class QixwebLoginNode extends WebNode
{
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
    {
        return new QixwebLoginNode();
    }

    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new QixwebLoginNode();
    }

    public boolean equals(Object obj)
    {
        return obj instanceof QixwebLoginNode;
    }
}
