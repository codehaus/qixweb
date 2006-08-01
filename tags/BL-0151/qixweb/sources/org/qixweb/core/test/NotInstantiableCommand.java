package org.qixweb.core.test;

import org.qixweb.core.*;


public class NotInstantiableCommand extends WebCommand
{
	public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
	{
        return null;
	}

	public Browsable execute(QixwebEnvironment system)
	{
        return null;
	}
}
