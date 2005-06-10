package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public class AnyCommand implements WebCommand
{
	public static WebCommand create(WebAppUrl anUrl, UserData aUserData)
	{
		return new AnyCommand();
	}

	public WebAppUrl execute(QixwebEnvironment system)
	{
		return new WebAppUrl(AnyNode.class, FakeSystem.BASE_URL);
	}
	
	public boolean equals(Object obj)
	{
		return DeepEquals.equals(this, obj);
	}

}
