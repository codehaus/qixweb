package org.qixweb.core.test;

import org.qixweb.core.*;

public class BadCreateCommand implements WebCommand
{
    public static final String FAKE_MESSAGE = "fake generated exception on create";


    public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
    {
        throw new RuntimeException(FAKE_MESSAGE);
    }


    public Browsable execute(QixwebEnvironment aEnvironment) throws Exception
    {
        return null;
    }

}
