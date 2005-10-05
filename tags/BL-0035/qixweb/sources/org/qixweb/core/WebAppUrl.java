package org.qixweb.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.block.Procedure;
import org.qixweb.util.UrlParametersExtractor;
import org.qixweb.util.XpLogger;



public class WebAppUrl extends WebUrl implements Browsable
{
    private static String itsServletPath = "";
    public static final WebAppUrl EMPTY_LINK = new WebAppUrl(Object.class, "");
    
	public static final WebAppUrl EMPTY_URL = new WebAppUrl(Object.class);
	public static final String PARAMETER_COMMAND_TO_EXECUTE = "command";
	public static final String PARAMETER_NODE_TO_DISPLAY = "node";
    

	private Class itsTargetClass;
    public static void initServletPath(String servletPath)
    {
        itsServletPath = servletPath;
    }
    
	public WebAppUrl(Class aTarget)
	{
		super(itsServletPath);
        resetParameters();
		itsTargetClass = aTarget;
		setClassNameParameterFor(aTarget);
	}

    public WebAppUrl(Class aTarget, String label)
    {
        super(itsServletPath, label);
        resetParameters();
        itsTargetClass = aTarget;
        setClassNameParameterFor(aTarget);
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

		if (WebCommand.class.isAssignableFrom(aTargetClass))
			setParameter(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE, className);
		else if (WebNode.class.isAssignableFrom(aTargetClass))
			setParameter(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, className);
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

	public WebCommand materializeTargetCommandWith(UserData userData)
	{
		Class[] createParameterTypes = new Class[] { WebAppUrl.class, UserData.class };
		Object[] createParameters = new Object[] { this, userData };

		return (WebCommand) callCreateOnTargetWith(createParameterTypes, createParameters);
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

	private static WebAppUrl tryToCreateUrlWithDestination(Map parametersMap, String aNodePackage, String aCommandPackage)
	{
		try
		{
			String targetClassName = extractDestinationFrom(parametersMap, aNodePackage, aCommandPackage);
			WebAppUrl webAppUrl = new WebAppUrl(Class.forName(targetClassName));
            return webAppUrl;
		}
		catch (Exception commandOrNodeNotFound)
		{
			XpLogger.logException(commandOrNodeNotFound);
			return WebAppUrl.EMPTY_URL;
		}
	}
	
	public static WebAppUrl createWithTarget(String aDestination, String aNodePackage, String aCommandPackage)
	{
		Map parameters = new UrlParametersExtractor(aDestination).run();
		return WebAppUrl.createFrom(parameters, aNodePackage, aCommandPackage);
	}	
	

	public static WebAppUrl createFrom(Map parametersMap, String aNodePackage, String aCommandPackage)
	{
		WebAppUrl mapAsUrl = tryToCreateUrlWithDestination(parametersMap, aNodePackage, aCommandPackage);
		mapAsUrl.setParameters(parametersMap);

		return mapAsUrl;
	}

	public static WebAppUrl createGhost(String label)
    {
        WebAppUrl ghostUrl = new WebAppUrl(Object.class, label);
        ghostUrl.disable();
        return ghostUrl;
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
