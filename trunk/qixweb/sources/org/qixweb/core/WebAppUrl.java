package org.qixweb.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.block.Procedure;
import org.qixweb.util.XpLogger;



public class WebAppUrl extends WebUrl implements Browsable
{
	public static final WebAppUrl EMPTY_URL = new WebAppUrl(Object.class, "");

	public static final String PARAMETER_COMMAND_TO_EXECUTE = "command";
	public static final String PARAMETER_NODE_TO_DISPLAY = "node";

	private Class itsTargetClass;
	private boolean isEnabled;

	public WebAppUrl(Class aTarget, String anUrl)
	{
		super(anUrl);
        resetParameters();
		itsTargetClass = aTarget;
		isEnabled = true;
		setClassNameParameterFor(aTarget);
	}

	public WebAppUrl(String anUrl)
	{
		super(anUrl);

		isEnabled = true;
	}
	public Class target()
	{
		return itsTargetClass;
	}
    
    public void displayThrough(ResponseHandler aResponseHandler) throws IOException
    {
        aResponseHandler.redirectTo(this);
    }

	private void setClassNameParameterFor(Class aTargetClass)
	{
		String fullName = aTargetClass.getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);

		if (WebRefreshableCommand.class.isAssignableFrom(aTargetClass))
			setParameter(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE, className);
		else if (WebNode.class.isAssignableFrom(aTargetClass))
			setParameter(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, className);
	}

	// @PMD:REVIEWED:OverrideBothEqualsAndHashcode: by bop on 3/8/05 12:38 PM
	public boolean equals(Object anotherObject)
	{
		if (anotherObject instanceof WebAppUrl)
		{
			WebAppUrl anotherUrl = (WebAppUrl) anotherObject;
			return super.equals(anotherObject) && isEnabled() == anotherUrl.isEnabled();
		}
		else
			return false;
	}

	public String toString()
	{
		return super.toString() + " enabled = " + isEnabled;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void disable()
	{
		isEnabled = false;
	}

	public boolean isExecutingACommand()
	{
		return getParameter(PARAMETER_COMMAND_TO_EXECUTE) != null;
	}
	public boolean isGoingToANode()
	{
		return getParameter(PARAMETER_NODE_TO_DISPLAY) != null;
	}

	public WebNode materializeTargetNodeWith(UserData userData, TheSystem system)
	{
		Class[] createParameterTypes = new Class[] { WebAppUrl.class, UserData.class, TheSystem.class };
		Object[] createParameters = new Object[] { this, userData, system };

		return (WebNode) callCreateOnTargetWith(createParameterTypes, createParameters);
	}
    public WebNode materializeTargetNodeWith(UserData userData, QixwebEnvironment environment)
    {
        Class[] createParameterTypes = new Class[] { WebAppUrl.class, UserData.class, QixwebEnvironment.class };
        Object[] createParameters = new Object[] { this, userData, environment };

        return (WebNode) callCreateOnTargetWith(createParameterTypes, createParameters);
    }

	private Object callCreateOnTargetWith(Class[] createParameterTypes, Object[] createParameters)
	{
		Object node = null;
		try
		{
			Method factoryMethod = target().getDeclaredMethod("create", createParameterTypes);
			node = factoryMethod.invoke(null, createParameters);
		}
		catch (InvocationTargetException itex)
		{
			XpLogger.logException(itex.getTargetException());
		}
		catch (Exception ex)
		{
			XpLogger.logException(ex);
		}
		return node;
	}

	public WebRefreshableCommand materializeTargetCommandWith(UserData userData)
	{
		Class[] createParameterTypes = new Class[] { WebAppUrl.class, UserData.class };
		Object[] createParameters = new Object[] { this, userData };

		return (WebRefreshableCommand) callCreateOnTargetWith(createParameterTypes, createParameters);
	}

	private static String extractDestinationFrom(Map parametersMap, String aNodePackage, String aCommandPackage)
	{
		String destination = null;

		if (parametersMap.get(WebAppUrl.PARAMETER_NODE_TO_DISPLAY) != null)
		{
			String nodeClassName = ((String[]) parametersMap.get(WebAppUrl.PARAMETER_NODE_TO_DISPLAY))[0];
			destination = aNodePackage + nodeClassName;
		}
		else if (parametersMap.get(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE) != null)
		{
			String commandClassName = ((String[]) parametersMap.get(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE))[0];
			destination = aCommandPackage + commandClassName;
		}
		return destination;
	}

	private static WebAppUrl tryToCreateUrlWithDestination(Map parametersMap, String aNodePackage, String aCommandPackage, String aBaseUrl)
	{
		try
		{
			String targetClassName = extractDestinationFrom(parametersMap, aNodePackage, aCommandPackage);
			return new WebAppUrl(Class.forName(targetClassName), aBaseUrl);
		}
		catch (Exception commandOrNodeNotFound)
		{
			XpLogger.logException(commandOrNodeNotFound);
			return WebAppUrl.EMPTY_URL;
		}
	}
	
	public static WebAppUrl createWithTarget(String aDestination, String aNodePackage, String aCommandPackage, String aBaseUrl)
	{
		Map parameters = extractParametersFrom(aDestination);
		return WebAppUrl.createFrom(parameters, aNodePackage, aCommandPackage, aBaseUrl);
	}	
	

	public static WebAppUrl createFrom(Map parametersMap, String aNodePackage, String aCommandPackage, String aBaseUrl)
	{
		WebAppUrl mapAsUrl = tryToCreateUrlWithDestination(parametersMap, aNodePackage, aCommandPackage, aBaseUrl);
		mapAsUrl.setParameters(parametersMap);

		return mapAsUrl;
	}

	public void copyOptionalParametersFrom(final WebUrl aUrl)
    {
        LightInternalIterator.createOn(aUrl.itsParameters.keySet()).forEach(new Procedure()
        {
            public void run(Object aEach)
            {
                String key = (String)aEach;
                boolean isOptional =    !key.equals(PARAMETER_COMMAND_TO_EXECUTE) &&
                                        !key.equals(PARAMETER_NODE_TO_DISPLAY);
                if (isOptional)
                {
                    Object object = aUrl.itsParameters.get(key);
                    itsParameters.put(key, object);
                }
            }
        });
    }
}
