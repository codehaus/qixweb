package org.qixweb.core.test;

import org.qixweb.core.*;


public class CommandWithValidation extends WebCommand
{
    public static WebCommand create(QixwebUrl notUsedUrl, UserData notUsedUserData)
    {
        return new CommandWithValidation();
    }
    
	public Browsable execute(QixwebEnvironment notUsed)
	{
        return new QixwebUrl(AnyNode.class);
	}

}
