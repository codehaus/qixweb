package org.qixweb.core.test;

import org.qixweb.core.QixwebEnvironment;
import org.qixweb.core.TheSystem;

public class FakeEnvironment extends QixwebEnvironment
{
    public TheSystem system()
    {
        return new FakeSystem();
    }

    public String nodePackage()
    {
        return "org.qixweb.core.test.";
    }

    public String commandPackage()
    {
        return "org.qixweb.core.test.";
    }

    public String servletPath()
    {
        return "home";
    }
}
