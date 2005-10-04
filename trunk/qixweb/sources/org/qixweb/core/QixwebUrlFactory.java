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
		return WebAppUrl.createFrom(parametersMap, itsEnvironment.nodePackage(), itsEnvironment.commandPackage());
	}

    public WebAppUrl createUrlWith(Class aClazz)
    {
        return WebAppUrl.createFor(aClazz);
    }
}
