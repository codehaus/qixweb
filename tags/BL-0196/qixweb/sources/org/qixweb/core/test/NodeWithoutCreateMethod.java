package org.qixweb.core.test;

import org.qixweb.core.WebNode;

public class NodeWithoutCreateMethod extends WebNode
{
    public boolean equals(Object obj)
    {
        return obj instanceof NodeWithoutCreateMethod;
    }
}
