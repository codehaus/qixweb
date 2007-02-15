package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.core.validation.WebNodeBuilder;

public class NodeWithoutCreateMethodBuilder extends WebNodeBuilder
{
    private static boolean hasBeenCalled = false;

    public NodeWithoutCreateMethodBuilder(QixwebEnvironment qixwebEnvironment)
    {
        super(qixwebEnvironment);
    }

    public WebNode createFrom(QixwebUrl urlToNode)
    {
        hasBeenCalled = true;
        return new NodeWithoutCreateMethod();
    }
    
    public static boolean hasBeenCalled()
    {
        return hasBeenCalled;
    }

    public static void reset()
    {
        hasBeenCalled = false;
    }
}
