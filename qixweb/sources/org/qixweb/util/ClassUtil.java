package org.qixweb.util;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

public class ClassUtil 
{
    private static final String INNER_CLASS_SEPARATOR = ".";

    public static String fullNameOf(Class aClass)
    {
        return aClass.getName();
    }
    
    public static String shortNameOf(Class aClass)
    {
        String shortClassName = ClassUtils.getShortClassName(aClass);
        
        if (ClassUtils.isInnerClass(aClass))
            return outerClassName(shortClassName);
        else 
            return shortClassName;
    }

    private static String outerClassName(String shortClassName)
    {
        return StringUtils.substringBefore(shortClassName, INNER_CLASS_SEPARATOR);
    }

    public static Object newInstance(Class clazz, Class[] classesOfArgs, Object[] args)
    {
        Object newInstance = null;
        try
        {
            Constructor constructor = clazz.getConstructor(classesOfArgs);
            newInstance = constructor.newInstance(args);
        }
        catch (Exception impossibleEx)
        {
			XpLogger.logException(impossibleEx);        
        }
        return newInstance;
    }
}
