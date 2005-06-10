package org.qixweb.core;

import java.io.Serializable;

public abstract class WebSystem implements TheSystem, Serializable
{
    private QixwebWorkgroup itsWorkgroup;

    public WebSystem()
    {
        itsWorkgroup = new QixwebWorkgroup();
    }
    
    public QixwebUser userBy(String aUserName)
    {
        return itsWorkgroup.findUserBy(aUserName);
    }
    
    protected QixwebWorkgroup workgroup()
    {
        return itsWorkgroup;
    }
    
    protected void resetWorkgroup()
    {
        itsWorkgroup = new QixwebWorkgroup();
    }
}
