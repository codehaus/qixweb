package org.qixweb.core.test;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public class AnyCommand implements WebCommand
{
    private String itsState;

    public AnyCommand(String state)
    {
        itsState = state;
    }
    
	public static WebCommand create(WebAppUrl anUrl, UserData aUserData)
	{
		Object state = aUserData.valueFor("state");
        return new AnyCommand(StringUtils.defaultString((String) state));
	}

	public Browsable execute(QixwebEnvironment system)
	{
		WebAppUrl webAppUrl = new WebAppUrl(AnyNode.class, FakeSystem.BASE_URL);
        webAppUrl.setParameter("state", itsState);
        return webAppUrl;
	}
	
	public boolean equals(Object obj)
	{
		return DeepEquals.equals(this, obj);
	}

}
