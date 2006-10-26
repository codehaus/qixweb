package org.qixweb.core;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.validation.WebCommandBuilder;
import org.qixweb.util.*;

public class QixwebUrl extends WebUrl implements Browsable
{
    private static final String COMMAND_BUILDER_SUFFIX = "Builder";

    private static String itsServletPath = "";
    private static String itsNodePackage = "";
    private static String itsCommandPackage = "";

    public static final QixwebUrl EMPTY_URL = new QixwebUrl(Object.class);
    public static final String PARAMETER_COMMAND_TO_EXECUTE = "command";
    public static final String PARAMETER_NODE_TO_DISPLAY = "node";

    private Class itsTargetClass;

    public static void initWith(String servletPath, String nodePackage, String commandPackage)
    {
        itsServletPath = servletPath;
        itsNodePackage = nodePackage;
        itsCommandPackage = commandPackage;
    }

    public static void initServletPathAndDefaultNodePackage(String servletPath)
    {
        initWith(servletPath, "", "");
    }

    public QixwebUrl(Class aTarget)
    {
        super(itsServletPath);
        initialize(aTarget);
    }

    public QixwebUrl(Class aTarget, String label)
    {
        super(itsServletPath, label);
        initialize(aTarget);
    }

    private void initialize(Class aTarget)
    {
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
        String className = ClassUtil.shortNameOf(aTargetClass);
        String fullName = ClassUtil.fullNameOf(aTargetClass);

        if (WebCommand.class.isAssignableFrom(aTargetClass))
        {
            if (!itsCommandPackage.equals(""))
                className = StringUtils.substringAfterLast(fullName, itsCommandPackage);
            parameters().set(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE, className);
        }

        else if (WebNode.class.isAssignableFrom(aTargetClass))
        {
            if (!itsNodePackage.equals(""))
                className = StringUtils.substringAfterLast(fullName, itsNodePackage);

            parameters().set(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, className);
        }
    }

    public boolean isExecutingACommand()
    {
        return parameters().get(PARAMETER_COMMAND_TO_EXECUTE) != null;
    }

    public boolean isGoingToANode()
    {
        return parameters().get(PARAMETER_NODE_TO_DISPLAY) != null;
    }

    public WebNode materializeTargetNodeWith(UserData userData, TheSystem system)
    {
        Class[] createParameterTypes = new Class[] { QixwebUrl.class, UserData.class, TheSystem.class };
        Object[] createParameters = new Object[] { this, userData, system };

        return (WebNode) CreateMethodInvoker.on(target(), createParameterTypes, createParameters);
    }

    public WebCommand materializeTargetCommandWith(UserData userData)
    {
        Class[] createParameterTypes = new Class[] { QixwebUrl.class, UserData.class };
        Object[] createParameters = new Object[] { this, userData };

        return (WebCommand) CreateMethodInvoker.on(target(), createParameterTypes, createParameters);
    }

    private static String extractDestinationFrom(Map parametersMap, String aNodePackage, String aCommandPackage)
    {
        String destination = null;

        if (parametersMap.get(QixwebUrl.PARAMETER_NODE_TO_DISPLAY) != null)
        {
            String nodeClassName = ((String[]) parametersMap.get(QixwebUrl.PARAMETER_NODE_TO_DISPLAY))[0];
            destination = aNodePackage + nodeClassName;
        }
        else if (parametersMap.get(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE) != null)
        {
            String commandClassName = ((String[]) parametersMap.get(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE))[0];
            destination = aCommandPackage + commandClassName;
        }
        return destination;
    }

    private static QixwebUrl tryToCreateUrlWithDestination(Map parametersMap, String aNodePackage, String aCommandPackage)
    {
        try
        {
            String targetClassName = extractDestinationFrom(parametersMap, aNodePackage, aCommandPackage);
            QixwebUrl webAppUrl = new QixwebUrl(Class.forName(targetClassName));
            return webAppUrl;
        }
        catch (Exception commandOrNodeNotFound)
        {
            XpLogger.logException(commandOrNodeNotFound);
            return QixwebUrl.EMPTY_URL;
        }
    }

    public static QixwebUrl createAsRequestWithTarget(String aDestination, String aNodePackage, String aCommandPackage)
    {
        Map parameters = new UrlParametersExtractor(aDestination).run();
        return createAsRequestFrom(parameters, aNodePackage, aCommandPackage);
    }

    public static QixwebUrl createAsRequestFrom(Map parametersMap, String aNodePackage, String aCommandPackage)
    {
        QixwebUrl mapAsUrl = tryToCreateUrlWithDestination(parametersMap, aNodePackage, aCommandPackage);
        mapAsUrl.parameters().set(parametersMap);

        return mapAsUrl;
    }

    public static QixwebUrl createGhost(String label)
    {
        QixwebUrl ghostUrl = new QixwebUrl(Object.class, label);
        ghostUrl.disable();
        return ghostUrl;
    }

    public void addOptionalParametersFrom(final WebUrl source)
    {
        parameters().addExcluding(source.parameters(), CollectionUtil.setWith(PARAMETER_COMMAND_TO_EXECUTE, PARAMETER_NODE_TO_DISPLAY));
    }

    public WebCommandBuilder toCommandBuilder()
    {
        String relatedRequestClassName = target().getName() + COMMAND_BUILDER_SUFFIX;
        try
        {
            Class relatedRequestClass = Class.forName(relatedRequestClassName);
            Class[] constructorParameterTypes = new Class[] { Parameters.class };
            Object[] constructorParameterValues = new Object[] { parameters() };

            return (WebCommandBuilder) ClassUtil.newInstance(relatedRequestClass, constructorParameterTypes, constructorParameterValues);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
