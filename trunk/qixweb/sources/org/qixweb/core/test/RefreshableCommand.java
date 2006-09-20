package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public class RefreshableCommand extends WebCommand
{
	public static RefreshableCommand create(QixwebUrl webUrl, UserData userData)
	{
		return new RefreshableCommand();
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
