package org.qixweb.core.test;

import org.qixweb.core.*;

public class FakeNode extends WebNode
{
    public static boolean createMethodHasBeenCalled = false;

    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        createMethodHasBeenCalled = true;
        return new FakeNode();
    }
}
