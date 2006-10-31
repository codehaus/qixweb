package org.qixweb.core.test;

import org.qixweb.core.*;

public class AnyCommand extends WebCommand
{
    private boolean hasBeenInstantiatedByCreate;
    
    public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
    {
        return new AnyCommand(true);
    }

    private AnyCommand(boolean instantiedByCreate)
    {
        hasBeenInstantiatedByCreate = instantiedByCreate;
    }

    public AnyCommand()
    {
        this(false);
    }
    
	public Browsable execute(QixwebEnvironment system)
	{
        return new QixwebUrl(AnyNode.class);
	}
	
	public boolean equals(Object obj)
	{
        return obj instanceof AnyCommand;
	}
    
    public boolean hasBeenInstantiatedUsingCreate()
    {
        return hasBeenInstantiatedByCreate;
    }
}
