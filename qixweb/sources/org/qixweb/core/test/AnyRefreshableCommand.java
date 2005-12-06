package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public class AnyRefreshableCommand extends WebCommand
{
	public static AnyRefreshableCommand create(QixwebUrl webUrl, UserData userData)
	{
		return new AnyRefreshableCommand();
	}
	
	public Browsable execute(QixwebEnvironment environment)
	{
		return new AnyNode();
	}
	
	public boolean equals(Object obj)
	{
		return DeepEquals.equals(this, obj);
	}

}
