package org.qixweb.core;


public abstract class QixwebEnvironment
{
    private QixwebSessionManager itsSessionManager;
    
    public abstract TheSystem system();

    public QixwebEnvironment()
    {
		// @PMD:REVIEWED:ConstructorCallsOverridableMethod: by bop on 1/25/05 12:24 PM
		initSessionManager();	
    }
    
    protected void initSessionManager()
    {
        itsSessionManager = new QixwebSessionManager();
    }

    public QixwebSessionManager sessionManager()
    {
    	return itsSessionManager;
    }

    public String velocityTemplateDir()
    {
        return "./templateVelocity/";
    }

    public abstract String nodePackage();

    public abstract String commandPackage();
}
