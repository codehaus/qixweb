package org.qixweb.sample.servlet;

import org.qixweb.core.QixwebEnvironment;
import org.qixweb.core.QixwebServlet;
import org.qixweb.sample.SampleEnvironment;



public class SampleServlet extends QixwebServlet
{
    protected QixwebEnvironment instantiateEnvironment()
    {
        return SampleEnvironment.instance();
    }
}
