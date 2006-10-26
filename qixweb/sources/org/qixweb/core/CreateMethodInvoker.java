package org.qixweb.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.qixweb.util.XpLogger;



public class CreateMethodInvoker
{
    public static WebNode onNode(QixwebUrl aQixwebUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return onNode(aQixwebUrl.target(), aQixwebUrl, aUserData, environment);
    }
    
    public static WebNode onNode(Class nodeClass, QixwebUrl aQixwebUrl, UserData aUserData, QixwebEnvironment environment)
    {
        Class[] createParameterTypes = new Class[] { QixwebUrl.class, UserData.class, QixwebEnvironment.class };
        Object[] createParameters = new Object[] { aQixwebUrl, aUserData, environment };

        return (WebNode) on(nodeClass, createParameterTypes, createParameters);
    }
    
    
    public static Object on(Class target, Class[] createParameterTypes, Object[] createParameters)
    {
        Object object = null;
        try
        {
            Method factoryMethod = target.getDeclaredMethod("create", createParameterTypes);
            object = factoryMethod.invoke(null, createParameters);
        }
        catch (InvocationTargetException itex)
        {
            XpLogger.logException(itex.getTargetException());
        }
        catch (Exception ex)
        {
            XpLogger.logException(ex);
        }
        return object;
    }

   
}
