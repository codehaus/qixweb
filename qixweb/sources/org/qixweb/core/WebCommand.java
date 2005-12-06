package org.qixweb.core;

import java.io.Serializable;

public abstract class WebCommand implements Serializable
{
    public abstract Browsable execute(QixwebEnvironment environment) throws Exception;
    
    public boolean canBeExecutedBy(QixwebUser aUser)
    {
        return true;
    }
}
