package org.qixweb.core.test.support;

import org.qixweb.core.QixwebEnvironment;
import org.qixweb.core.TheSystem;

public class FakeEnvironment extends QixwebEnvironment
{
    private boolean areResourcesFreed = true;
    private boolean systemHasBeenInvoked = false;

    public TheSystem system()
    {
        systemHasBeenInvoked  = true;
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

    public void freeResources()
    {
        areResourcesFreed = true;
    }

    public boolean areResourcesFree()
    {
        return areResourcesFreed;
    }

    public void lockResources()
    {
        areResourcesFreed = false;
    }

    public boolean hasSystemBeenInvoked()
    {
        return systemHasBeenInvoked;
    }
}
