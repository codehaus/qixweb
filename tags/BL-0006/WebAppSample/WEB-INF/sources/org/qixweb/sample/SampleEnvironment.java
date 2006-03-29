package org.qixweb.sample;

import org.qixweb.core.QixwebEnvironment;
import org.qixweb.core.TheSystem;


public class SampleEnvironment extends QixwebEnvironment
{

    public TheSystem system()
    {
        return new TheSystem() {};
    }

    public static SampleEnvironment instance()
    {
        return new SampleEnvironment();
    }

    public String nodePackage()
    {
        return "org.qixweb.sample.node.";
    }
    public String commandPackage()
    {
        return "org.qixweb.sample.command.";
    }
    public String servletPath()
    {
        return "/webappsample/servlet/SampleServlet";
    }


}
