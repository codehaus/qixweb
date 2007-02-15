package org.qixweb.core;

import java.util.Map;



public class QixwebUrlFactory
{
    private QixwebEnvironment itsEnvironment;

    public QixwebUrlFactory(QixwebEnvironment anEnvironment)
    {
        itsEnvironment = anEnvironment;
    }
    
	public QixwebUrl createFrom(Map parametersMap)
	{
		return QixwebUrl.createAsRequestFrom(parametersMap, itsEnvironment.nodePackage(), itsEnvironment.commandPackage());
	}

    public QixwebUrl createUrlWith(Class aClazz)
    {
        return new QixwebUrl(aClazz);
    }
}
