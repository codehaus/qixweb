package org.qixweb.core;

import java.util.Map;



public class QixwebUrlFactory
{
    private QixwebEnvironment itsEnvironment;

    public QixwebUrlFactory(QixwebEnvironment anEnvironment)
    {
        itsEnvironment = anEnvironment;
    }
    
	public WebAppUrl createFrom(Map parametersMap)
	{
		return WebAppUrl.createFrom(parametersMap, itsEnvironment.nodePackage(), itsEnvironment.commandPackage(), itsEnvironment.servletPath());
	}

    public WebAppUrl createUrlWith(Class aClazz)
    {
        return new WebAppUrl(aClazz, itsEnvironment.servletPath());
    }
}
