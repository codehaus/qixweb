package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.*;
import org.qixweb.core.test.support.FakeEnvironment;

public class FakeCommandVerifyingResouceLocking extends WebCommand
{
    public Browsable execute(QixwebEnvironment anEnvironment)
    {
        FakeEnvironment environment = (FakeEnvironment) anEnvironment;
        TestCase.assertFalse("In this context the resources should be locked", environment.areResourcesFree());
        return new QixwebUrl(AnyNode.class);
    }

    public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
    {
        return new FakeCommandVerifyingResouceLocking();
    }
}