package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;

public class WrongLinkNodeForTest extends WebNode
{
    public static WebNode create(QixwebUrl anUrl, UserData aUserData, TheSystem system)
    {
        return new WrongLinkNodeForTest();
    }

    public boolean equals(Object obj)
    {
        return DeepEquals.equals(this, obj);
    }
}
