package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public class AnyRefreshableCommand implements WebRefreshableCommand
{
	public static WebRefreshableCommand create(WebAppUrl webUrl)
	{
		return new AnyRefreshableCommand();
	}
	
	public WebNode execute(QixwebEnvironment environment)
	{
		return new AnyNode();
	}
	
	public boolean equals(Object obj)
	{
		return DeepEquals.equals(this, obj);
	}

}
