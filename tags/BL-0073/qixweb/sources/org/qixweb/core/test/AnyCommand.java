package org.qixweb.core.test;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public class AnyCommand extends WebCommand
{
    private String itsState;

    public AnyCommand(String state)
    {
        itsState = state;
    }
    
	public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
	{
		Object state = aUserData.valueFor("state");
        return new AnyCommand(StringUtils.defaultString((String) state));
	}

	public Browsable execute(QixwebEnvironment system)
	{
		QixwebUrl webAppUrl = new QixwebUrl(AnyNode.class);
        webAppUrl.setParameter("state", itsState);
        return webAppUrl;
	}
	
	public boolean equals(Object obj)
	{
		return DeepEquals.equals(this, obj);
	}

}
