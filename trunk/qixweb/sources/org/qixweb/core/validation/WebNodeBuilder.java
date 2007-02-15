package org.qixweb.core.validation;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;

public abstract class WebNodeBuilder
{
    private QixwebEnvironment itsQixwebEnvironment;

    public WebNodeBuilder(QixwebEnvironment qixwebEnvironment)
    {
        itsQixwebEnvironment = qixwebEnvironment;
    }
    
    protected QixwebEnvironment qixwebEnvironment()
    {
        return itsQixwebEnvironment;
    }
    
    public abstract WebNode createFrom(QixwebUrl urlToNode);
    
    public boolean equals(Object other)
    {
        return DeepEquals.equals(this, other);
    }
}
