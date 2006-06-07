package org.qixweb.util;

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
}
